package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.dao.OrderDao;
import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.model.Order;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@Component
public class CartomancyManager {


    @Autowired
    private PredictionManager predictionManager;

    private TelegramBot bot;

    private final Logger log = LoggerFactory.getLogger(CartomancyManager.class);

    public void startService(Long chatId, Long serviceId, TelegramBot bot) {
        this.bot = bot;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();
    }


















/*
    private void startAutoService(Order order, TelegramBot bot) {
        List<String> cardNames = predictionManager.getCardNames();
        int randomIndex = (int)(Math.random() *cardNames.size());
        String cardName = cardNames.get(randomIndex);
        TarotAnswer answer = new TarotAnswer(order, cardName);
        try{
            sendTrueAnswer(order.getOrderId().getChatId(), bot, answer);
        }
        catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
*/
}
