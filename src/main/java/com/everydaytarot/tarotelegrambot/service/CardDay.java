package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardDay implements ServiceProvider {
    public SERVICE_STATE start(Order order) {
        return SERVICE_STATE.SUCCESS;
    }

    @Override
    public List<String> getCategory() {
        return null;
    }
}
