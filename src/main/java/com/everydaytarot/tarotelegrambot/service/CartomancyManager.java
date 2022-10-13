package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.dao.AnswerDao;
import com.everydaytarot.tarotelegrambot.dao.OrderDao;
import com.everydaytarot.tarotelegrambot.dao.QuestionDao;
import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.model.Order;
import com.everydaytarot.tarotelegrambot.model.Question;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CartomancyManager {


    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PredictionManager predictionManager;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private QuestionDao questionDao;

    private TelegramBot bot;

    private final Logger log = LoggerFactory.getLogger(CartomancyManager.class);

    public void start(Long chatId, Long serviceId) {
        Order newOrder = createOrder(chatId, serviceId);
        Question question = createQuestion(newOrder);
    }

    private Order createOrder(Long chatId, Long serviceId) {
        Order newOrder = new Order();
        newOrder.setChatId(chatId);
        newOrder.setServiceId(serviceId);
        newOrder = orderDao.save(newOrder);
        return newOrder;
    }

    private Question createQuestion(Order order) {
        Question question = new Question();
        question.setDate(new Date(System.currentTimeMillis()));
        question.setOrderId(order.getId());
        question.setCategoryPrediction(stateDao.getSelectAugury(order.getChatId()));
        return questionDao.save(question);
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
