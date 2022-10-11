package com.everydaytarot.tarotelegrambot.business;

import com.everydaytarot.tarotelegrambot.dao.*;
import com.everydaytarot.tarotelegrambot.model.service.Order;
import com.everydaytarot.tarotelegrambot.model.service.OrderId;
import com.everydaytarot.tarotelegrambot.model.service.Service;
import com.everydaytarot.tarotelegrambot.service.ImageManager;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
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
            this.image = imageManager.getFileImageCard(cardName, shirt);
            this.description = getDescription(order);
        }

        public String getShirt(Order order) {
            String shirtUser = userDao.getUser(order.getOrderId().getChatId()).getShirt();
            List<String> shirts = imageManager.getAllCardShirt();
            if(shirts.contains(shirtUser))
                return shirtUser;
            else if(shirts.size()>0)
                return shirts.get(0);
            else
                return null;
        }

        public String getDescription(Order order) {
            String category = order.getCategory();
            List<String> allCategory = auguryResultDao.getAllCategory();
            if(!allCategory.contains(category))
                category = allCategory.get(0);
            String description = auguryResultDao.getAugury(this.cardName, category);
            return description;
        }
    }

    @Autowired
    AuguryResultDao auguryResultDao;

    @Autowired
    ServiceDao serviceDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    StateDao stateDao;

    @Autowired
    ImageManager imageManager;

    private final Logger log = LoggerFactory.getLogger(CartomancyManager.class);

    public List<String> getTypesAugury() {
        return auguryResultDao.getAllCategory();
    }

    public List<String> getListServiceName() {
        List<String> listServiceName = new ArrayList<>();
        List<Service> listService = serviceDao.getAllService();
        for(Service service : listService) {
            listServiceName.add(service.getName());
        }
        return listServiceName;
    }

    public Service getService(String serviceName) {

        return serviceDao.getService(serviceName);
    }

    public void startService(String chatId, String serviceName, TelegramBot bot) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OrderId orderId = new OrderId(Long.valueOf(chatId), serviceName);
                    Order findOrder = orderDao.findOrder(orderId);
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
                }
            }
        });
        thread.start();
    }

    private void startAutoService(Order order, TelegramBot bot) {
        List<String> cardNames = auguryResultDao.getCardNames();
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

    private void sendTrueAnswer(Long chatId, TelegramBot bot, TarotAnswer answer) throws TelegramApiException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setCaption(answer.description);
        InputFile inputFile = new InputFile(answer.image);
        sendPhoto.setPhoto(inputFile);
        bot.execute(sendPhoto);
    }

    public void sendFalseAnswer(String chatId, TelegramBot bot, String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(chatId, text);
        bot.execute(sendMessage);
    }

    private void useOrder(Order order) {
        order.setLastUse(new Date(System.currentTimeMillis()));
        order.setCountUse(order.getCountUse() + 1);
        order.setAllUse(order.getAllUse() + 1);
        orderDao.save(order);
    }

    private Order createOrder(String chatId, String serviceName) {
        Service service = getService(serviceName);
        int countDay = service.getCountDay();
        int countUse = service.getCountUse();
        OrderId orderId = new OrderId(Long.valueOf(chatId), serviceName);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setLastUse(new Date(System.currentTimeMillis()));
        order.setEndUse(new Timestamp(System.currentTimeMillis() + 86400000L * countDay));
        order.setMaxUse(service.getCountUse());
        order.setMaxAllUse(service.getMaxUse());
        order.setPrice(service.getPrice());
        order.setCountUse(0);
        order.setAllUse(0);
        order.setCategory(stateDao.getSelectAugury(Long.valueOf(chatId)));
        orderDao.save(order);
        return order;
    }

    private CHECKED_STATE checkedOrder(Order order) {

        Timestamp endUse = order.getEndUse();
        Timestamp currentTimeDate = new Timestamp(System.currentTimeMillis());
        boolean isEndUse = endUse.compareTo(currentTimeDate)>0;
        if(!isEndUse)
            return CHECKED_STATE.END_DATE_FAILED;

        int allUseService = order.getAllUse();
        int maxAllUseService = order.getMaxAllUse();
        boolean isAllUse = allUseService<maxAllUseService;
        if(!isAllUse)
            return CHECKED_STATE.ALL_USE_FAILED;

        Date currentDate = new Date(System.currentTimeMillis());
        Date lastUse = order.getLastUse();
        boolean isLastUse = currentDate.compareTo(lastUse)>0;
        if(!isLastUse) {
            int useService = order.getCountUse();
            int maxUseService = order.getMaxAllUse();
            boolean isCountUse = useService<maxUseService;
            if(!isCountUse)
                return CHECKED_STATE.COUNT_USE_FAILED;
        }

        return CHECKED_STATE.ACTIVE;
    }
}
