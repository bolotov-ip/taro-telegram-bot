package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.model.Order;

import java.util.List;

public interface ServiceProvider {

    public List<String> getCategory();

    public SERVICE_STATE start(Order order);
}
