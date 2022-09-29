package com.everydaytarot.tarotelegrambot.service.monitor;

import com.everydaytarot.tarotelegrambot.model.Order;
import com.everydaytarot.tarotelegrambot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitorOrder {

    @Autowired
    private OrderRepository orderRepository;

    public void addOrder() {
        Order order = Order.createOrder(0L, 0L);
        orderRepository.save(order);
    }

}
