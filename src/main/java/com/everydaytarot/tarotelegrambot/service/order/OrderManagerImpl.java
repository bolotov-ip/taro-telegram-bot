package com.everydaytarot.tarotelegrambot.service.order;

import com.everydaytarot.tarotelegrambot.model.order.Order;
import com.everydaytarot.tarotelegrambot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderManagerImpl implements OrderManger {

    @Autowired
    private OrderRepository orderRepository;

    public void addNewOrder(String chatId) {

    }

    public boolean removeOrder(String chatId) {
        return true;
    }

    public List<Order> getAllOrder() {
        return  null;
    }

    @Override
    public boolean addOrder() {

        return false;
    }
}
