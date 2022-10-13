package com.everydaytarot.tarotelegrambot.config;

public enum SERVICE_TYPE {
    TIP_DAY("Совет дня"),
    CARTOMANCY("Индивидуальная карта");

    private String text;

    SERVICE_TYPE(String txt) {
        text = txt;
    }

    public String getText() {
        return text;
    }
}
