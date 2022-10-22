package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.dao.OrderDao;
import com.everydaytarot.tarotelegrambot.dao.QueryDao;
import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.model.Order;
import com.everydaytarot.tarotelegrambot.model.Query;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CardIndividual {


    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SettingsManager settingsManager;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private QueryDao queryDao;

    private TelegramBot bot;

    private final Logger log = LoggerFactory.getLogger(CardIndividual.class);

    public SERVICE_STATE start(Order order) {
        return SERVICE_STATE.SUCCESS;
    }

    private Order createOrder(Long chatId, Long serviceId) {
        Order newOrder = new Order();
        newOrder.setChatId(chatId);
        newOrder.setServiceId(serviceId);
        newOrder = orderDao.save(newOrder);
        return newOrder;
    }

    private Query createQuestion(Order order) {
        Query query = new Query();
        query.setDate(new Date(System.currentTimeMillis()));
        query.setOrderId(order.getId());
        query.setCategoryPrediction(stateDao.getSelectAugury(order.getChatId()));
        return queryDao.save(query);
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
