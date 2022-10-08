package com.everydaytarot.tarotelegrambot.service.card_day;

import com.everydaytarot.tarotelegrambot.dao.OrderDao;
import com.everydaytarot.tarotelegrambot.dao.ServiceDao;
import com.everydaytarot.tarotelegrambot.model.service.Order;
import com.everydaytarot.tarotelegrambot.model.service.OrderId;
import com.everydaytarot.tarotelegrambot.model.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardOfTheDayService {


    @Autowired
    ServiceDao serviceDao;

    @Autowired
    OrderDao orderDao;

    public void sendAnswer(DefaultAbsSender bot, Order order, String card) {

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

    public String startService(String chatId, String serviceName) {
        OrderId orderId = new OrderId(Long.valueOf(chatId), serviceName);
        Order findOrder = orderDao.findOrder(orderId);
        if(findOrder == null)
            createOrder(chatId, serviceName);
        else {

        }
        return null;
    }

    public Order createOrder(String chatId, String serviceName) {
        Service service = getService(serviceName);
        int countDay = service.getCountDay();
        int countUse = service.getCountUse();
        OrderId orderId = new OrderId(Long.valueOf(chatId), serviceName);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setDayLastUse(new Date(System.currentTimeMillis()));
        order.setDayEndUse(new Timestamp(System.currentTimeMillis() + 86400000 * countDay));
        order.setCountUseDay(countUse);
        return order;
    }

    public boolean checkedOrder(Order order) {
        boolean isEndDayChecked = order.getDayEndUse().compareTo(new Timestamp(System.currentTimeMillis()))>0;
        return false;
    }
}
