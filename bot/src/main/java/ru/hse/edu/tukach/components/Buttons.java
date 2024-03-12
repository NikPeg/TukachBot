package ru.hse.edu.tukach.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("\uD83C\uDFE0Главное меню");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("\uD83C\uDD98Помощь");
    private static final InlineKeyboardButton REQUEST_BUTTON = new InlineKeyboardButton("✍\uFE0FОтправить заявку");
    private static final InlineKeyboardButton LIST_BUTTON = new InlineKeyboardButton("\uD83D\uDCD1Список заявок");
    private static final InlineKeyboardButton MORE_BUTTON = new InlineKeyboardButton("\uD83D\uDC40Подробности");

    public static InlineKeyboardMarkup homeInlineMarkup() {
        START_BUTTON.setCallbackData("/start");

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

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

    public static InlineKeyboardMarkup moreInlineMarkup() {
        MORE_BUTTON.setCallbackData("more");

        List<InlineKeyboardButton> firstRow = List.of(MORE_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(firstRow);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
