package com.everydaytarot.tarotelegrambot.telegram.constant;

public enum COMMANDS {
    COMMAND_START("/start");

    private String text;

    COMMANDS(String txt) {
        text = txt;
    }

    public String getText() {
        return text;
    }
}
