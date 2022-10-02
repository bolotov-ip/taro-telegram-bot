package com.everydaytarot.tarotelegrambot.telegram;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.dao.UserDao;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.model.user.User;
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

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired
    private AdminHandler adminHandler;

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private UserDao userDao;

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

        User user = userDao.getUser(msg);
        if(user==null) {
            user = userDao.registerUser(msg);
        }
        Handler handler = user.isAdmin()?adminHandler:userHandler;
        AnswerBot answer = handler.run();
        try {
            execute(answer.getAnswer());
        } catch (TelegramApiException e) {
            log.error("Error occured: " + e.getMessage());
        }
    }
}
