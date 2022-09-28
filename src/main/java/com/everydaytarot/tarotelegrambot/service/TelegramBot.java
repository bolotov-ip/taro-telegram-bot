package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.config.BotInizitalzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

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
        log.info("onUpdateReceived");
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            switch (messageText) {
                case "/start":
                    message.setText("Test bot");
                    break;
                default:
                    message.setText("Command undefined");
            }
            try {
                execute(message);
            }
            catch (TelegramApiException e) {
                log.error("Error occurred: " + e.getMessage());
            }
        }
    }
}
