package com.everydaytarot.tarotelegrambot.service.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewButton {
    public enum AdminButton{
        ADMIN_ORDER_BUTTON("Выполнить заказ"),
        ADMIN_MENU("Меню"),
        ADMIN_BACK("Назад");

        private String text;

        AdminButton(String txt) {
            text = txt;
        }

        String getText() {
            return text;
        }
    }

    public static void setStartButton(SendMessage msg) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(AdminButton.ADMIN_ORDER_BUTTON.getText());
        button.setCallbackData(AdminButton.ADMIN_ORDER_BUTTON.toString());
        rowInLine.add(button);
        button =new InlineKeyboardButton();
        button.setText(AdminButton.ADMIN_MENU.getText());
        button.setCallbackData(AdminButton.ADMIN_MENU.toString());
        rowInLine.add(button);
        rowsInLine.add(rowInLine);


        markupInLine.setKeyboard(rowsInLine);
        msg.setReplyMarkup(markupInLine);
    }
}
