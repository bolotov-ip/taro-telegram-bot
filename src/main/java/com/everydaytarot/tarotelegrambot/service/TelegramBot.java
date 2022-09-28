package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.model.User;
import com.everydaytarot.tarotelegrambot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private MainHandler mainHandler;

    private CallbackHandler callbackHandler;

    @Autowired
    BotConfig config;

    @Autowired
    UserRepository userRepository;

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            mainHandler = mainHandler==null?new MainHandler(this, update.getMessage()):mainHandler;
        } else if(update.hasCallbackQuery()) {
            callbackHandler = callbackHandler==null?new CallbackHandler(this):callbackHandler;
        }
    }
}
