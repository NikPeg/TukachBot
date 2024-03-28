package ru.hse.edu.tukach;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hse.edu.tukach.components.Buttons;
import ru.hse.edu.tukach.dto.application.ApplicationFromTelegramCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationLiteDto;
import ru.hse.edu.tukach.dto.application.ApplicationSource;
import ru.hse.edu.tukach.model.application.Application;
import ru.hse.edu.tukach.model.application.ApplicationType;
import ru.hse.edu.tukach.service.application.ApplicationService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.hse.edu.tukach.components.BotCommands.LIST_OF_COMMANDS;

@RequiredArgsConstructor
public class TukachBot extends TelegramLongPollingBot {

    private final ApplicationService service;
    private ApplicationFromTelegramCreationDto application;

    void sendMessage(Message message, String textToSend, ReplyKeyboard markup) {
        SendMessage sendMessage = new SendMessage(); // Create a SendMessage object with mandatory fields
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(textToSend);
        sendMessage.enableHtml(true);
        sendMessage.disableWebPagePreview();
        ReplyKeyboardRemove remove = new ReplyKeyboardRemove(true);
        sendMessage.setReplyMarkup(remove);
        if (markup != null) {
            sendMessage.setReplyMarkup(markup);
        }
        try {
            execute(sendMessage); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void deleteMessage(Message originalMessage) {
        DeleteMessage message = new DeleteMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(originalMessage.getChatId().toString());
        message.setMessageId(originalMessage.getMessageId());
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void editMessage(Message originalMessage, String textToSend, InlineKeyboardMarkup markup) {
        EditMessageText editText = new EditMessageText();
        editText.setChatId(originalMessage.getChatId().toString());
        editText.setMessageId(originalMessage.getMessageId());
        editText.setText(textToSend);
        editText.enableHtml(true);
        editText.disableWebPagePreview();

        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setMessageId(originalMessage.getMessageId());
        editMarkup.setChatId(originalMessage.getChatId().toString());
        if (markup != null) {
            editMarkup.setReplyMarkup(markup);
        }
        try {
            execute(editText); // Call method to edit the message
            execute(editMarkup); // Call method to edit the message markup
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startCommandReceived(Message originalMessage) {
        String answer = "\uD83D\uDC4BДобрый день, " + originalMessage.getChat().getFirstName() + "!\n" +
                "\uD83D\uDE0EЯ бот системы приема заявок нарушения корпоративной этики. " +
                "Я здесь, чтобы помочь вам поддерживать высокие стандарты профессионального поведения в нашем " +
                "коллективе.\n" +
                "❓<b>Выберите нужное действие:</b>";
        if (originalMessage.getFrom().getIsBot()) {
            editMessage(originalMessage, answer, Buttons.startInlineMarkup());
        }
        else {
            sendMessage(originalMessage, answer, Buttons.startInlineMarkup());
        }
    }

    private void requestCommandReceived(Message originalMessage) {
        String answer = "\uD83D\uDD25Система проверки нарушения корпоративной этики гарантирует, что Ваша заявка " +
                "будет защищена и не будет доступна третьим лицам.\n<b>Выберите тип заявки:</b>";
        deleteMessage(originalMessage);
        sendMessage(originalMessage, answer, Buttons.typesKeyboardMarkup());
        this.application = new ApplicationFromTelegramCreationDto();
        this.application.setInitiatorTg(originalMessage.getChatId().toString());
        this.application.setCurrentField("type");
    }

    private void helpCommandReceived(Message originalMessage) {
        String answer = "😊Вы обратились в систему приема заявок по вопросам нарушений корпоративной этики. Для того чтобы оформить заявку, необходимо ввести следующую информацию:\n" +
            "• \uD83E\uDD35\u200D♂\uFE0FВаши ФИО;\n" +
            "• \uD83D\uDD54Время нарушения;\n" +
            "• \uD83D\uDDFAМесто нарушения;\n" +
            "• \uD83D\uDCDDПодробное описание нарушения;\n" +
            "• 📸Также вы имеете возможность приложить фото и видео доказательства, если они имеются.\n\n" +
            "📤Чтобы начать ввод данных заявки, нажмите кнопку \"✍\uFE0FОтправить заявку\" и ваш запрос будет немедленно направлен в специализированный отдел для рассмотрения и принятия соответствующих мер.\n\n" +
            "\uD83D\uDE4FПожалуйста, внесите всю необходимую информацию в четкой и ясной форме, чтобы мы могли максимально быстро и эффективно рассмотреть вашу заявку. 🕒\n\n" +
            "👏Благодарим вас за стремление поддерживать корпоративную этику в нашей компании. Если у вас возникнут дополнительные вопросы, не стесняйтесь обращаться к администратору бота: @nikpeg. 🤖\n";
        if (originalMessage.getFrom().getIsBot()) {
            editMessage(originalMessage, answer, Buttons.homeInlineMarkup());
        }
        else {
            sendMessage(originalMessage, answer, Buttons.homeInlineMarkup());
        }
    }

    private void listCommandReceived(Message originalMessage) {
        List<ApplicationLiteDto> applications = service.getAllApplicationsByInitiatorTg(originalMessage.getChatId().toString());
        if (applications.isEmpty()) {
            String answer = "💫Пока Ваш список заявок пуст! Отправьте первую заявку, чтобы пополнить его.";
            editMessage(originalMessage, answer, Buttons.homeInlineMarkup());
        }
        else {
            String answer = "\uD83D\uDCD5Список Ваших заявок:";
            editMessage(originalMessage, answer, null);
            for (ApplicationLiteDto application : applications) {
                answer = "<b>Заявка №" + application.getId() + "</b>" +
                        "\nТип заявки: " + application.getType() +
                        "\nТема заявки: " + application.getTopic() +
                        "\nСтатус заявки: " + application.getStatus();
                sendMessage(originalMessage, answer, Buttons.moreInlineMarkup());
            }
            String more = "Нажмите кнопку \"\uD83D\uDC40Подробности\" под интересующей заявкой, чтобы узнать полную информацию о ней.\n\n" +
                    "Нажмите \"\uD83D\uDDD1Удалить\", чтобы удалить ошибочно отправленную заявку.";
            sendMessage(originalMessage, more, Buttons.homeInlineMarkup());
        }
    }

    private long applicationNumberFrom(Message message) throws RuntimeException {
        Pattern pattern = Pattern.compile("Заявка №(\\d+)");
        Matcher matcher = pattern.matcher(message.getText());
        // Check if the pattern is found in the text
        if (matcher.find()) {
            // Extract and print the application number
            String applicationNumber = matcher.group(1);
            return Long.parseLong(applicationNumber);
        } else {
            throw new RuntimeException("Bad message received!");
        }
    }

    private String generateSlug() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        int lengthToGenerate = 15;
        for (int i = 0; i < lengthToGenerate; i++) {
            result.append(characters.charAt((int) Math.floor(Math.random() * characters.length())));
        }

        return result.toString();
    }

    private void moreCommandReceived(Message originalMessage) {
        long applicationId;
        try {
            applicationId = applicationNumberFrom(originalMessage);
        }
        catch (RuntimeException e) {
            sendMessage(originalMessage, "Что-то пошло не так!", Buttons.homeInlineMarkup());
            return;
        }
        Application application = service.getApplicationByIdAndInitiator(applicationId, originalMessage.getChatId().toString(), ApplicationSource.TELEGRAM);
        String answer = "<b>Заявка №" + application.getId() + "</b>" +
                "\nТип заявки: " + application.getType() +
                "\nТема заявки: " + application.getTopic() +
                "\nСтатус заявки: " + application.getStatus() +
                "\nВремя создания: " + application.getCreatedDateTime() +
                "\nОписание заявки: " + application.getDescription() +
                "\nОтвет на заявку: " + application.getReviewerResponse() +
                "\nСсылка на заявку: http://localhost:4200/response/" + generateSlug();
        sendMessage(originalMessage, answer, Buttons.homeInlineMarkup());
    }

    private void deleteCommandReceived(Message originalMessage) {
        long applicationId;
        try {
            applicationId = applicationNumberFrom(originalMessage);
        }
        catch (RuntimeException e) {
            sendMessage(originalMessage, "Что-то пошло не так!", Buttons.homeInlineMarkup());
            return;
        }

        service.deleteApplication(applicationId);

        deleteMessage(originalMessage);
        String answer = "\uD83D\uDC4DЗаявка №" + applicationId + " успешно удалена!";
        sendMessage(originalMessage, answer, Buttons.homeInlineMarkup());
    }

    private void unknownCommandReceived(Message originalMessage) {
        if (this.application != null && this.application.getInitiatorTg().equals(originalMessage.getChatId().toString())) {
            applicationCompletionReceived(originalMessage);
        }
        else {
            String answer = "\uD83E\uDD37\u200D♂\uFE0FЯ пока не знаю такой команды. " +
                    "<b>Выберите один из вариантов действий:</b>";
            sendMessage(originalMessage, answer, Buttons.startInlineMarkup());
        }
    }

    private void applicationCompletionReceived(Message originalMessage) {
        String answer = "";
        String messageText = originalMessage.getText();
        switch (this.application.getCurrentField()) {
            case "type":
                this.application.setType(ApplicationType.OTHER);
                for (ApplicationType type : ApplicationType.values()) {
                    if (messageText.endsWith(type.getMessage())) {
                        this.application.setType(type);
                        break;
                    }
                }
                this.application.setCurrentField("topic");
                answer = "Теперь введите тему заявки:";
                break;
            case "topic":
                this.application.setTopic(messageText);
                this.application.setCurrentField("description");
                answer = "Теперь введите описание заявки. Вы можете добавить ссылку на картинку с помощью сервисов для хранения картинок (например, imgbb.com):";
                break;
            case "description":
                this.application.setDescription(messageText);
                this.application.setCurrentField("fio");
                answer = "Теперь введите Ваше ФИО:";
                break;
            case "fio":
                this.application.setInitiatorFio(messageText);
                this.application.setCurrentField("all");
                answer = "❤\uFE0FСпасибо, заявка сохранена! Информация о заявке:\n" +
                         "Тип заявки: " + this.application.getType() +
                         "\nТема заявки: " + this.application.getTopic() +
                         "\nТекст заявки: " + this.application.getDescription() +
                         "\nВаши ФИО: " + this.application.getInitiatorFio() +
                         "\nСсылка на заявку: " + generateSlug() +
                         "\n<b>Заявка в обработке, ожидайте!</b>";
                service.save(application);
                break;
            default:
                answer = "Что-то пошло не так...";
                break;
        }
        sendMessage(originalMessage, answer, Buttons.homeInlineMarkup());
    }

    @Override
    public void onUpdateReceived(Update update) {
        String updateText;
        Message originalMessage;

        //если получено сообщение текстом
        if(update.hasMessage()) {
            originalMessage = update.getMessage();

            if (update.getMessage().hasText()) {
                updateText = update.getMessage().getText();
                botAnswerUtils(updateText, originalMessage);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            updateText = update.getCallbackQuery().getData();
            originalMessage = update.getCallbackQuery().getMessage();
            botAnswerUtils(updateText, originalMessage);
        }
    }

    private void botAnswerUtils(String callback, Message originalMessage) {
        switch (callback) {
            case "/start":
                startCommandReceived(originalMessage);
                break;
            case "request":
                requestCommandReceived(originalMessage);
                break;
            case "/help":
                helpCommandReceived(originalMessage);
                break;
            case "list":
                listCommandReceived(originalMessage);
                break;
            case "more":
                moreCommandReceived(originalMessage);
                break;
            case "delete":
                deleteCommandReceived(originalMessage);
                break;
            default:
                unknownCommandReceived(originalMessage);
                break;
        }
    }

    @Override
    public String getBotUsername() {
        return "TukachBot";
    }

    @Override
    public String getBotToken() {
        return "6799271405:AAEsMGd4ka3FbqTGC20xTUyEAx-gYbQcN7U";
    }
}
