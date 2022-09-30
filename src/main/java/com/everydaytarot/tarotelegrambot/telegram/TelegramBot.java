package com.everydaytarot.tarotelegrambot.telegram;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.model.Role;
import com.everydaytarot.tarotelegrambot.model.User;
import com.everydaytarot.tarotelegrambot.repository.UserRepository;
import com.everydaytarot.tarotelegrambot.telegram.handler.AdminHandler;
import com.everydaytarot.tarotelegrambot.telegram.handler.Handler;
import com.everydaytarot.tarotelegrambot.telegram.handler.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired
    private AdminHandler adminHandler;

    @Autowired
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
        adminHandler.setBot(this);
        adminHandler.setUpdate(update);
        userHandler.setBot(this);
        userHandler.setUpdate(update);
        Handler handler = isAdmin(msg)?adminHandler:userHandler;
        AnswerBot answer = handler.run();
        try {
            execute(answer.getAnswer());
        } catch (TelegramApiException e) {
            log.error("Error occured: " + e.getMessage());
        }
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
