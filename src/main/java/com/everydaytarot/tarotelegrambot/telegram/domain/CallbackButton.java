package com.everydaytarot.tarotelegrambot.telegram.domain;

import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;

public class CallbackButton {

    String stringButton;
    BUTTONS enumButton;

    public CallbackButton(String name) {
        stringButton = name;
    }

    public CallbackButton(BUTTONS name) {
        enumButton = name;
    }

    public String getCallbackData() {
        if(enumButton!=null)
            return enumButton.toString();
        else
            return stringButton;
    }

    public String getText() {
        if(enumButton!=null)
            return enumButton.getText();
        else
            return stringButton;
    }
}
