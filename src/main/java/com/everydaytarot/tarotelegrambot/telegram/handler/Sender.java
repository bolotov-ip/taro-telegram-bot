package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Sender {

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    public static void sendMessage(TelegramBot bot, SendMessage message) {
        try {
            bot.execute(message);
        }
        catch (TelegramApiException e) {
            log.error("sendMessage error occurred: " + e.getMessage());
        }
    }

    public static void sendPhoto(TelegramBot bot, SendPhoto photo) {
        try {
            bot.execute(photo);
        }
        catch (TelegramApiException e) {
            log.error("sendPhoto error occurred: " + e.getMessage());
        }
    }

    public static void sendDocument(TelegramBot bot, SendDocument doc) {
        try {
            bot.execute(doc);
        }
        catch (TelegramApiException e) {
            log.error("sendDocument error occurred: " + e.getMessage());
        }
    }
}
