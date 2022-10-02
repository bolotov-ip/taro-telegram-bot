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

public class MessageButtonView {

    public static void setMessageButton(BotApiMethod<?> msg, String botState) {

        if(botState.equals(STATE_BOT.USER_MENU.toString())) {
            setUserMenu(msg);
        }
        else if(botState.equals(STATE_BOT.START.toString())) {
            setAdminStart(msg);
        }
        else if(botState.equals(STATE_BOT.ADMIN_MENU.toString())) {
            setAdminMenu(msg);
        }
        else if(botState.equals(STATE_BOT.INPUT_XLSX.toString())) {
            setAdminAddXlsx(msg);
        }
        else if(botState.equals(STATE_BOT.LOAD.toString())) {
            setLoad(msg);
        }
        else if(botState.equals(STATE_BOT.ERROR_LOAD.toString())) {
            setErrorLoad(msg);
        }
        else if(botState.equals(STATE_BOT.ADMIN_SERVICE_MENU.toString())) {
            setAdminServiceMenu(msg);
        }

    }

    private static void setAdminStart(BotApiMethod<?> msg) {

        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_ADMIN_ORDER_BUTTON);
        listBtn.add(BUTTONS.BTN_ADMIN_MENU);
        setButton(listBtn, msg, 2);
    }


    private static void setAdminMenu(BotApiMethod<?> msg) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_BACK);
        listBtn.add(BUTTONS.BTN_ADMIN_ADD_XLSX);
        listBtn.add(BUTTONS.BTN_ADMIN_SERVICE);
        setButton(listBtn, msg, 2);

    }

    public static void setAdminServiceMenu(BotApiMethod<?> msg) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_BACK);
        listBtn.add(BUTTONS.BTN_ADMIN_SHOW_SERVICE);
        listBtn.add(BUTTONS.BTN_ADMIN_ADD_SERVICE);
        setButton(listBtn, msg, 2);
    }

    private static void setAdminAddXlsx(BotApiMethod<?> msg) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_CANCEL);
        setButton(listBtn, msg, 2);
    }

    private static void setLoad(BotApiMethod<?> msg) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_BACK_TO_START);
        setButton(listBtn, msg, 2);
    }

    private static void setErrorLoad(BotApiMethod<?> msg) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_BACK_TO_START);
        listBtn.add(BUTTONS.BTN_ADMIN_AGAIN_LOAD);
        setButton(listBtn, msg, 2);
    }

    private static void setUserMenu(BotApiMethod<?> msg) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_USER_MENU);
        listBtn.add(BUTTONS.BTN_USER_AUGURY);
        setButton(listBtn, msg, 2);
    }

    private static void replyMarkup(InlineKeyboardMarkup markupInLine, BotApiMethod<?> msg) {
        if(msg instanceof SendMessage)
            ((SendMessage)msg).setReplyMarkup(markupInLine);
        else if(msg instanceof EditMessageText) {
            ((EditMessageText)msg).setReplyMarkup(markupInLine);
        }
    }

    private static void setButton(List<BUTTONS> btnList, BotApiMethod<?> msg, int countColumn) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        int countAddRowBtn = 0;
        for(BUTTONS btn : btnList) {
            if(countAddRowBtn<countColumn){
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(btn.getText());
                button.setCallbackData(btn.toString());
                rowInLine.add(button);
                countAddRowBtn++;
            }
            else {
                rowsInLine.add(rowInLine);
                rowInLine = new ArrayList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(btn.getText());
                button.setCallbackData(btn.toString());
                rowInLine.add(button);
                countAddRowBtn=1;
            }
        }
        if(!rowsInLine.contains(rowInLine))
            rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        replyMarkup(markupInLine, msg);
    }
}
