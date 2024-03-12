package ru.hse.edu.tukach;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hse.edu.tukach.components.Buttons;
import ru.hse.edu.tukach.dto.application.ApplicationFromTelegramCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationLiteDto;
import ru.hse.edu.tukach.model.application.ApplicationType;
import ru.hse.edu.tukach.service.application.ApplicationService;

import java.util.List;

import static ru.hse.edu.tukach.components.BotCommands.LIST_OF_COMMANDS;

@RequiredArgsConstructor
public class TukachBot extends TelegramLongPollingBot {

    private final ApplicationService service;
    private ApplicationFromTelegramCreationDto application;

//    public TukachBot() {
//        try {
//            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
//        } catch (TelegramApiException e){
//            e.printStackTrace();
//        }
//        this.application = new ApplicationFromTelegramCreationDto();
////        this.service = service;
//    }
    void sendMessage(Long chatId, String textToSend, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId.toString());
        message.setText(textToSend);
        message.enableHtml(true);
        if (markup != null) {
            message.setReplyMarkup(markup);
        }

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "\uD83D\uDC4BДобрый день, " + name + "!\n" +
                "\uD83D\uDE0EЯ бот системы приема заявок нарушения корпоративной этики. " +
                "Я здесь, чтобы помочь вам поддерживать высокие стандарты профессионального поведения в нашем " +
                "коллективе.\n" +
                "❓<b>Выберите нужное действие:</b>";
        sendMessage(chatId, answer, Buttons.startInlineMarkup());
    }

    private void requestCommandReceived(Long chatId) {
        String answer = "\uD83D\uDD25Система проверки нарушения корпоративной этики гарантирует, что Ваша заявка " +
                "будет защищена и не будет доступна третьим лицам.\n<b>Выберите тип заявки:</b>";
        sendMessage(chatId, answer, Buttons.homeInlineMarkup());
        this.application = new ApplicationFromTelegramCreationDto();
        this.application.setInitiatorTg(chatId.toString());
        this.application.setCurrentField("type");
//        service.save(application);
    }

    private void helpCommandReceived(Long chatId) {
        String answer = "😊Вы обратились в систему приема заявок по вопросам нарушений корпоративной этики. Для того чтобы оформить заявку, необходимо ввести следующую информацию:\n" +
            "• \uD83E\uDD35\u200D♂\uFE0FВаши ФИО;\n" +
            "• \uD83D\uDD54Время нарушения;\n" +
            "• \uD83D\uDDFAМесто нарушения;\n" +
            "• \uD83D\uDCDDПодробное описание нарушения;\n" +
            "• 📸Также вы имеете возможность приложить фото и видео доказательства, если они имеются.\n\n" +
            "📤Чтобы начать ввод данных заявки, нажмите кнопку \"✍\uFE0FОтправить заявку\" и ваш запрос будет немедленно направлен в специализированный отдел для рассмотрения и принятия соответствующих мер.\n\n" +
            "\uD83D\uDE4FПожалуйста, внесите всю необходимую информацию в четкой и ясной форме, чтобы мы могли максимально быстро и эффективно рассмотреть вашу заявку. 🕒\n\n" +
            "👏Благодарим вас за стремление поддерживать корпоративную этику в нашей компании. Если у вас возникнут дополнительные вопросы, не стесняйтесь обращаться к администратору бота: @nikpeg. 🤖\n";
        sendMessage(chatId, answer, Buttons.homeInlineMarkup());
    }

    private void listCommandReceived(Long chatId) {
        List<ApplicationLiteDto> applications = service.getAllApplicationsByInitiatorTg(chatId.toString());
        if (applications.isEmpty()) {
            String answer = "💫Пока Ваш список заявок пуст! Отправьте первую заявку, чтобы пополнить его.";
            sendMessage(chatId, answer, Buttons.homeInlineMarkup());
        }
        else {
            String answer = "\uD83D\uDCD5Список Ваших заявок:";
            sendMessage(chatId, answer, null);
            for (ApplicationLiteDto application : applications) {
                answer = "<b>Заявка №" + application.getId() + "</b>" +
                        "\nТип заявки: " + application.getType() +
                        "\nТема заявки: " + application.getTopic() +
                        "\nСтатус заявки: " + application.getStatus();
                sendMessage(chatId, answer, Buttons.moreInlineMarkup());
            }
            String more = "Нажмите кнопку \"\uD83D\uDC40Подробности\" под интересующей заявкой, чтобы узнать полную информацию о ней.";
            sendMessage(chatId, more, Buttons.homeInlineMarkup());
        }
    }

    private void unknownCommandReceived(Long chatId, String receivedMessage) {
        if (this.application.getInitiatorTg().equals(chatId.toString())) {
            applicationCompletionReceived(chatId, receivedMessage);
        }
        else {
            String answer = "\uD83E\uDD37\u200D♂\uFE0FЯ пока не знаю такой команды. " +
                    "<b>Выберите один из вариантов действий:</b>";
            sendMessage(chatId, answer, Buttons.startInlineMarkup());
        }
    }

    private void applicationCompletionReceived(Long chatId, String receivedMessage) {
        String answer = "";
        switch (this.application.getCurrentField()) {
            case "type":
                this.application.setType(ApplicationType.OTHER);
                for (ApplicationType type : ApplicationType.values()) {
                    if (type.getMessage().equals(receivedMessage)) {
                        this.application.setType(type);
                        break;
                    }
                }
                answer = "Теперь введите тему заявки:";
                break;
            case "topic":
                this.application.setTopic(receivedMessage);
                this.application.setCurrentField("description");
                answer = "Теперь введите описание заявки:";
                break;
            case "description":
                this.application.setDescription(receivedMessage);
                this.application.setCurrentField("fio");
                answer = "Теперь введите Ваше ФИО:";
                break;
            case "fio":
                this.application.setInitiatorFio(receivedMessage);
                this.application.setCurrentField("all");
                answer = "❤\uFE0FСпасибо, заявка сохранена! Информация о заявке:\n" +
                         "Тип заявки: " + this.application.getType() +
                         "\nТема заявки: " + this.application.getTopic() +
                         "\nТекст заявки: " + this.application.getDescription() +
                         "\nВаши ФИО: " + this.application.getInitiatorFio() +
                         "\n<b>Заявка в обработке, ожидайте!</b>";
                service.save(application);
                break;
            default:
                answer = "Что-то пошло не так...";
                break;
        }
        sendMessage(chatId, answer, Buttons.homeInlineMarkup());
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        long userId = 0; //это нам понадобится позже
        String userName = null;
        String receivedMessage;

        //если получено сообщение текстом
        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, userName);
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage) {
            case "/start":
                startCommandReceived(chatId, userName);
                break;
            case "request":
                requestCommandReceived(chatId);
                break;
            case "/help":
                helpCommandReceived(chatId);
                break;
            case "list":
                listCommandReceived(chatId);
                break;
            default:
                unknownCommandReceived(chatId, receivedMessage);
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
