package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.model.User;
import com.everydaytarot.tarotelegrambot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MainHandler implements Handler {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private TelegramBot bot;

    private Message message;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BotConfig config;

    public MainHandler(TelegramBot bot, Message msg) {
        this.bot = bot;
        this.message = msg;
    }

    public void run() {

    }

    public void registerUser(Message msg) {
        if(userRepository.findById(msg.getChatId()).isEmpty()){
            boolean isOwner = msg.getChatId().equals(config.getOwnerId());
            User user = isOwner?User.createUserAdmin(msg):User.createUser(msg);
            userRepository.save(user);
            log.info("user saved: " + user);
        }
    }
}
