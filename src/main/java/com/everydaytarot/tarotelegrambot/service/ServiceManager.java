package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.model.Subs;

public interface ServiceManager {

    public void start(SERVICE_TYPE serviceType, Subs subs);

}
