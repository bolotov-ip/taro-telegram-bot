package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.model.Subs;

public interface ServiceManager {

    public SERVICE_STATE start(Long chatId, Subs subs);

}
