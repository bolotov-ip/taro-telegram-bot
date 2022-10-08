package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.service.Order;
import com.everydaytarot.tarotelegrambot.model.service.OrderId;
import com.everydaytarot.tarotelegrambot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderDao {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOrder(OrderId orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent())
            return order.get();
        return null;
    }

    public Order getActiveOrder() {
        String sql = "select * from user_use_service as o where o.";
        return  null;
    }
}
