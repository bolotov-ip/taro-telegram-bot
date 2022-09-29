package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
