package com.everydaytarot.tarotelegrambot.telegram.domain;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AnswerBot {
    BotApiMethod<?> ans;

    public AnswerBot() {
    }

    public AnswerBot(BotApiMethod<?> answer) {
        ans = answer;
    }

    public BotApiMethod<?> getAnswer() {
        return ans;
    }

    public void setAnswer(BotApiMethod<?> answer) {
        this.ans = answer;
    }
}
