package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.service.OrderId;
import com.everydaytarot.tarotelegrambot.model.service.Order;
import org.springframework.data.repository.CrudRepository;

public interface UserUseServiceRepository extends CrudRepository<Order, OrderId> {
}
