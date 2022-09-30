package com.everydaytarot.tarotelegrambot.telegram.constant;

public enum COMANDS {
    COMMAND_START("/start");

    private String text;

    COMANDS(String txt) {
        text = txt;
    }

    public String getText() {
        return text;
    }
}
