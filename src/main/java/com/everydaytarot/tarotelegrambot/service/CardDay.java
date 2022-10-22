package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.model.Order;
import org.springframework.stereotype.Component;

@Component
public class CardDay {
    public SERVICE_STATE start(Order order) {
        return SERVICE_STATE.SUCCESS;
    }
}
