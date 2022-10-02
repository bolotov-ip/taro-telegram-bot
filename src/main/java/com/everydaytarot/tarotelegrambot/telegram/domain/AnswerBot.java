package com.everydaytarot.tarotelegrambot.telegram.domain;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;

public class AnswerBot {
    BotApiMethod<?> message;
    SendDocument document;

    public SendDocument getDocument() {
        return document;
    }

    public void setDocument(SendDocument document) {
        this.document = document;
    }

    public AnswerBot() {
    }

    public AnswerBot(BotApiMethod<?> answer) {
        message = answer;
    }

    public BotApiMethod<?> getMessage() {
        return message;
    }

    public void setMessage(BotApiMethod<?> answer) {
        this.message = answer;
    }

    public boolean hasMessage() {
        if(message !=null)
            return true;
        return false;
    }

    public boolean hasDocument() {
        if(document !=null)
            return true;
        return false;
    }
}
