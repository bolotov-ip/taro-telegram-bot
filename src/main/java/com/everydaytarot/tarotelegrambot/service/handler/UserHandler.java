package com.everydaytarot.tarotelegrambot.service.handler;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.repository.UserRepository;
import com.everydaytarot.tarotelegrambot.service.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserHandler implements Handler{

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private TelegramBot bot;

    private Update update;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BotConfig config;

    public UserHandler(TelegramBot bot, Update update) {
        this.bot = bot;
        this.update = update;
    }

    @Override
    public void run() {

    }
}
