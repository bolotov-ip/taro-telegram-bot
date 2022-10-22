package com.everydaytarot.tarotelegrambot.config;

public enum SERVICE_TYPE {
    CARTOMANCY("Индивидуальная карта"),
    CARD_OF_THE_DAY("Карта дня"),
    NUMEROLOGY("Нумерология"),
    ALIGMENT("Расклад"),
    QUESTION_YES_NO("Вопрос да/нет");

    private String text;

    SERVICE_TYPE(String txt) {
        text = txt;
    }

    public String getText() {
        return text;
    }
}
