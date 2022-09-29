package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.model.Role;
import com.everydaytarot.tarotelegrambot.model.User;
import com.everydaytarot.tarotelegrambot.repository.UserRepository;
import com.everydaytarot.tarotelegrambot.service.handler.AdminHandler;
import com.everydaytarot.tarotelegrambot.service.handler.Handler;
import com.everydaytarot.tarotelegrambot.service.handler.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private AdminHandler adminHandler;

    private UserHandler userHandler;

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
        Message msg = update.hasMessage()?update.getMessage():update.hasCallbackQuery()?update.getCallbackQuery().getMessage():null;
        if(msg == null)
            return;
        boolean isAdmin = isAdmin(msg);
        Handler handler = null;
        if(isAdmin) {
            handler = adminHandler = adminHandler==null?new AdminHandler(this, update):adminHandler;
        } else
            handler = userHandler = userHandler==null?new UserHandler(this, update):userHandler;
        handler.run();
    }

    private boolean isAdmin(Message msg) {
        Optional<User> optUser = userRepository.findById(msg.getChatId());
        User user = null;
        if(optUser.isPresent()) {
            user = optUser.get();
        } else {
            user = registerUser(msg);
        }
        boolean isAdmin = user.getRole().contains(Role.ADMIN.toString());
        if(isAdmin)
            return true;
        return false;
    }

    public User registerUser(Message msg) {
        boolean isOwner = String.valueOf(msg.getChatId()).equals(config.getOwnerId());
        User user = isOwner?User.createUserAdmin(msg):User.createUser(msg);
        userRepository.save(user);
        log.info("user saved: " + user);
        return user;
    }
}
