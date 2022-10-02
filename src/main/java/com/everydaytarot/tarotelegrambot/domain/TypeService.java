package com.everydaytarot.tarotelegrambot.domain;

public enum TypeService {
    ANSWER_TO_CARD_QUESTION("Ответ на вопрос по картам");

    private String name;
    private TypeService(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
