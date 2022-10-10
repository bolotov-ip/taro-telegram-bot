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
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
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
        if(update.hasPreCheckoutQuery()) {
            AnswerPreCheckoutQuery answerPreCheckoutQuery = new AnswerPreCheckoutQuery(update.getPreCheckoutQuery().getId(), true);
            try {
                execute(answerPreCheckoutQuery);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }

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
            if(answer.hasMessage())
                execute(answer.getMessage());
            else if(answer.hasDocument())
                execute(answer.getDocument());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
