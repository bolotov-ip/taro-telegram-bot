package com.everydaytarot.tarotelegrambot.service;

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

    public enum CHECKED_STATE{
        COUNT_USE_FAILED, ALL_USE_FAILED, END_DATE_FAILED, ACTIVE;
    }

    public class TarotAnswer {
        String cardName;
        File image;
        String description;
        String shirt;

        public TarotAnswer(Order order, String cardName) {
            this.cardName = cardName;
            this.shirt = getShirt(order);
            this.image = null;
            this.description = getDescription(order);
        }

        public String getShirt(Order order) {
            String shirtUser = stateManager.getShirt(order.getChatId());
            List<String> shirts = null;
            if(shirts.contains(shirtUser))
                return shirtUser;
            else if(shirts.size()>0)
                return shirts.get(0);
            else
                return null;
        }

        public String getDescription(Order order) {
            String category = stateManager.getSelectAugury(order.getChatId());
            List<String> allCategory = predictionManager.getAllCategory();
            if(!allCategory.contains(category))
                category = allCategory.get(0);
            String description = predictionManager.getAugury(this.cardName, category);
            return description;
        }
    }

    @Autowired
    PredictionManager predictionManager;

    @Autowired
    ServiceManager serviceManager;

    @Autowired
    OrderManager orderManager;

    @Autowired
    UserManager userManager;

    @Autowired
    StateManager stateManager;


    private final Logger log = LoggerFactory.getLogger(CartomancyManager.class);

    public List<String> getTypesAugury() {
        return predictionManager.getAllCategory();
    }

    public void startService(String chatId, String serviceName, TelegramBot bot) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {/*
                try {
                    OrderId orderId = new OrderId(Long.valueOf(chatId), serviceName);
                    Order findOrder = orderManager.findOrder(orderId);
                    if(findOrder == null) {
                        Order newOrder = createOrder(chatId, serviceName);
                        startAutoService(newOrder, bot);
                        useOrder(newOrder);
                    }
                    else {
                        CHECKED_STATE checked_state = checkedOrder(findOrder);
                        if(checked_state.equals(CHECKED_STATE.END_DATE_FAILED)) {
                            // предлагаем произвести оплату
                            sendFalseAnswer(chatId, bot, "Срок действия услуги истек. Для использования требуется произвести оплату");
                        }
                        else if (checked_state.equals(CHECKED_STATE.ALL_USE_FAILED)) {
                            // предлагаем произвести оплату
                            sendFalseAnswer(chatId, bot, "Вы использовали все попытки на день");
                        }
                        else if(checked_state.equals(CHECKED_STATE.COUNT_USE_FAILED)) {
                            sendFalseAnswer(chatId, bot, "Вы использовали услугу максимальное количество раз за сегодняшний день.\nПопробуйте снова завтра");
                        }
                        else if (checked_state.equals(CHECKED_STATE.ACTIVE)) {
                            if(findOrder.getPrice()==0) {
                                startAutoService(findOrder, bot);
                                useOrder(findOrder);
                            }
                            else
                                sendFalseAnswer(chatId, bot, "Ваш заказ принят и находится в процессе работы");
                        }
                    }
                }
                catch (Exception e) {
                    log.error(e.getMessage());
                }*/
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
    private void sendTrueAnswer(Long chatId, TelegramBot bot, TarotAnswer answer) throws TelegramApiException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setCaption(answer.description);
        InputFile inputFile = new InputFile(answer.image);
        sendPhoto.setPhoto(inputFile);
        bot.execute(sendPhoto);
    }

}
