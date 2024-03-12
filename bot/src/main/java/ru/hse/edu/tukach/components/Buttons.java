package ru.hse.edu.tukach.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("\uD83C\uDFE0Главное меню");

    public static InlineKeyboardMarkup homeInlineMarkup() {
        START_BUTTON.setCallbackData("/start");

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("\uD83C\uDD98Помощь");
    private static final InlineKeyboardButton REQUEST_BUTTON = new InlineKeyboardButton("✍\uFE0FОтправить заявку");
    private static final InlineKeyboardButton LIST_BUTTON = new InlineKeyboardButton("\uD83D\uDCD1Список заявок");

    public static InlineKeyboardMarkup startInlineMarkup() {
        REQUEST_BUTTON.setCallbackData("request");
        HELP_BUTTON.setCallbackData("/help");
        LIST_BUTTON.setCallbackData("list");

        List<InlineKeyboardButton> firstRow = List.of(REQUEST_BUTTON);
        List<InlineKeyboardButton> secondRow = List.of(HELP_BUTTON, LIST_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(firstRow, secondRow);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
    private static final InlineKeyboardButton MORE_BUTTON = new InlineKeyboardButton("\uD83D\uDC40Подробности");
    public static InlineKeyboardMarkup moreInlineMarkup() {
        MORE_BUTTON.setCallbackData("more");

        List<InlineKeyboardButton> firstRow = List.of(MORE_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(firstRow);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
    private static final KeyboardButton VIOLATION_BUTTON = new KeyboardButton("\uD83D\uDE21Нарушение");
    private static final KeyboardButton COMPLIMENT_BUTTON = new KeyboardButton("\uD83D\uDE0DБлагодарность");
    private static final KeyboardButton OTHER_BUTTON = new KeyboardButton("\uD83E\uDD14Другое");
    public static ReplyKeyboardMarkup typesKeyboardMarkup() {
        KeyboardRow row = new KeyboardRow();
        row.add(VIOLATION_BUTTON);
        row.add(COMPLIMENT_BUTTON);
        row.add(OTHER_BUTTON);
        List<KeyboardRow> rows = List.of(row);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rows);
        markup.setOneTimeKeyboard(true);
        markup.setResizeKeyboard(true);

        return markup;
    }
}
