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
        ServiceProvider service = getService(SERVICE_TYPE.valueOf(subs.getType()));
        service.start(newOrder);
        return state;
    }

    @Override
    public ServiceProvider getService(SERVICE_TYPE serviceType) {
        switch (serviceType) {
            case CARD_INDIVIDUAL:
                return cardIndividual;
            case CARD_OF_THE_DAY:
                return cardDay;
        }
        return null;
    }

    public Order createOrder() {
        return null;
    }

    public SERVICE_STATE check(Long chatId, Subs subs) {
        return SERVICE_STATE.FAIL_ORDER;
    }
}
