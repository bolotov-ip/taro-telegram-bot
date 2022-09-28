package com.everydaytarot.tarotelegrambot.service;

public class CallbackHandler implements Handler {

    private TelegramBot bot;

    public CallbackHandler(TelegramBot bot) {
        this.bot = bot;
    }
}
