package com.everydaytarot.tarotelegrambot.telegram.view;

import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MenuView {
    public static void setMessageButton(SendMessage msg) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTONS.BTN_ADMIN_ORDER_BUTTON.getText());
        button.setCallbackData(BUTTONS.BTN_ADMIN_ORDER_BUTTON.toString());
        rowInLine.add(button);
        button =new InlineKeyboardButton();
        button.setText(BUTTONS.BTN_ADMIN_MENU.getText());
        button.setCallbackData(BUTTONS.BTN_ADMIN_MENU.toString());
        rowInLine.add(button);
        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        msg.setReplyMarkup(markupInLine);
    }

    public static void setEditMessageButton(EditMessageText msg) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTONS.BTN_ADMIN_BACK.getText());
        button.setCallbackData(BUTTONS.BTN_ADMIN_BACK.toString());
        rowInLine.add(button);
        button =new InlineKeyboardButton();
        button.setText(BUTTONS.BTN_ADMIN_ADD_XLSX.getText());
        button.setCallbackData(BUTTONS.BTN_ADMIN_ADD_XLSX.toString());
        rowInLine.add(button);
        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        msg.setReplyMarkup(markupInLine);
    }
}
