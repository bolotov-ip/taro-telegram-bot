package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.model.Order;
import com.everydaytarot.tarotelegrambot.model.Subs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceManagerImpl implements ServiceManager {

    @Autowired
    private CardIndividual cardIndividual;

    @Autowired
    private CardDay cardDay;

    @Override
    public SERVICE_STATE start(Long chatId, Subs subs) {
        SERVICE_STATE state = check(chatId, subs);
        if(!state.equals(SERVICE_STATE.SUCCESS))
            return state;

        Order newOrder = createOrder();
        switch (SERVICE_TYPE.valueOf(subs.getServiceType())) {
            case CARD_INDIVIDUAL:
                state = cardIndividual.start(newOrder);
                break;
            case CARD_OF_THE_DAY:
                state = cardDay.start(newOrder);
        }

        return state;
    }

    public Order createOrder() {
        return null;
    }

    public SERVICE_STATE check(Long chatId, Subs subs) {
        return SERVICE_STATE.FAIL_ORDER;
    }
}
