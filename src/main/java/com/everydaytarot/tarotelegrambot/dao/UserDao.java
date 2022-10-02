package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.model.user.User;
import com.everydaytarot.tarotelegrambot.repository.UserRepository;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class UserDao {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BotConfig config;

    public User registerUser(Message msg) {
        boolean isOwner = String.valueOf(msg.getChatId()).equals(config.getOwnerId());
        User user = isOwner?User.createUserAdmin(msg):User.createUser(msg);
        userRepository.save(user);
        log.info("user saved: " + user);
        return user;
    }

    public boolean hasUser(Message msg) {
        Optional<User> optUser = userRepository.findById(msg.getChatId());
        if(optUser.isPresent()) {
            return true;
        }
        return false;
    }

    public User getUser(Message msg) {
        Optional<User> optUser = userRepository.findById(msg.getChatId());
        if(optUser.isPresent())
            return optUser.get();
        return null;
    }
}
