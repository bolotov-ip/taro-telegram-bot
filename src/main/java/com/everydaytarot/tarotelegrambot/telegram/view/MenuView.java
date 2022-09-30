package com.everydaytarot.tarotelegrambot.telegram.view;

import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MenuView {

    public static void setMessageButton(BotApiMethod<?> msg, String botState) {

        if(botState.equals(STATE_BOT.START.toString())) {
            setAdminStart(msg);
        }
        else if(botState.equals(STATE_BOT.BTN_ADMIN_MENU.toString())) {
            setAdminMenu(msg);
        }
        else if(botState.equals(STATE_BOT.INPUT_XLSX.toString())) {
            setAdminAddXlsx(msg);
        }
    }

    private static void setAdminStart(BotApiMethod<?> msg) {
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
        replyMarkup(markupInLine, msg);
    }


    private static void setAdminMenu(BotApiMethod<?> msg) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTONS.BTN_BACK.getText());
        button.setCallbackData(BUTTONS.BTN_BACK.toString());
        rowInLine.add(button);
        button =new InlineKeyboardButton();
        button.setText(BUTTONS.BTN_ADMIN_ADD_XLSX.getText());
        button.setCallbackData(BUTTONS.BTN_ADMIN_ADD_XLSX.toString());
        rowInLine.add(button);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        replyMarkup(markupInLine, msg);
    }

    private static void setAdminAddXlsx(BotApiMethod<?> msg) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(BUTTONS.BTN_BACK.getText());
        button.setCallbackData(BUTTONS.BTN_BACK.toString());
        rowInLine.add(button);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        replyMarkup(markupInLine, msg);
    }

    private static void replyMarkup(InlineKeyboardMarkup markupInLine, BotApiMethod<?> msg) {
        if(msg instanceof SendMessage)
            ((SendMessage)msg).setReplyMarkup(markupInLine);
        else if(msg instanceof EditMessageText) {
            ((EditMessageText)msg).setReplyMarkup(markupInLine);
        }
    }
}
