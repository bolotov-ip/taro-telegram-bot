package com.everydaytarot.tarotelegrambot.config;

public enum SERVICE_TYPE {
    CARD_INDIVIDUAL("Индивидуальная карта"),
    CARD_OF_THE_DAY("Карта дня"),
    NUMEROLOGY("Нумерология"),
    ALIGNMENT("Расклад"),
    QUESTION_YES_NO("Вопрос да/нет"),
    ORACLE("Оракул");

    private String text;

    SERVICE_TYPE(String txt) {
        text = txt;
    }

    public String getText() {
        return text;
    }
}
