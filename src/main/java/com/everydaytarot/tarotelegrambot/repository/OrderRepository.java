package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.service.Order;
import com.everydaytarot.tarotelegrambot.model.service.OrderId;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, OrderId> {
}
