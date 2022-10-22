package com.everydaytarot.tarotelegrambot.telegram.event;


import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.dao.SubsDao;
import com.everydaytarot.tarotelegrambot.service.SettingsManager;
import com.everydaytarot.tarotelegrambot.model.Subs;
import com.everydaytarot.tarotelegrambot.service.Cartomancy;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.domain.CallbackButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventUser extends Event {

    @Autowired
    Cartomancy cartomancy;

    @Autowired
    SubsDao subsDao;

    @Autowired
    SettingsManager settingsManager;


    public AnswerBot start(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        for (SERVICE_TYPE serviceType : SERVICE_TYPE.values()) {
            CallbackButton btn = new CallbackButton(serviceType.getText());
            btn.setCallbackData(serviceType.toString());
            listBtn.add(btn);
        }
        int column = listBtn.size()>2?1:2;
        return setAnswer(update, STATE_BOT.USER_START, listBtn, column);
    }

    public AnswerBot getListService(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        SERVICE_TYPE serviceType = stateDao.getServiceType(update.getCallbackQuery().getMessage().getChatId());
        List<Subs> activeSubs = subsDao.getActiveServices(serviceType);
        for(Subs subs : activeSubs) {
            CallbackButton btn = new CallbackButton(subs.getName());
            btn.setCallbackData(subs.getId().toString());
            listBtn.add(btn);
        }
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        return setAnswer(update, STATE_BOT.USER_SERVICE_LIST, listBtn, 1);
    }

    public AnswerBot getServiceDetails(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        stateDao.setSelectService(Long.valueOf(callbackData), chatId);
        Subs subs = subsDao.getService(chatId);
        String description = subs.getDescription();
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        listBtn.add(new CallbackButton(BUTTONS.BTN_USER_START_SERVICE));
        AnswerBot answer = setAnswer(update, STATE_BOT.USER_SERVICE_DETAILS, listBtn, 2);
        ((EditMessageText) answer.getMessage()).setText(description);
        return answer;
    }

    public AnswerBot getCategoryPrediction(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        SERVICE_TYPE service_type = stateDao.getServiceType(update.getCallbackQuery().getMessage().getChatId());
        List<String> listTypeAugury = settingsManager.getAllCategory(service_type);
        List<CallbackButton> listBtn = new ArrayList<>();
        for(String type : listTypeAugury) {
            listBtn.add(new CallbackButton(type));
        }
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        AnswerBot answer = setAnswer(update, STATE_BOT.USER_SELECT_CATEGORY, listBtn, 2);
        return answer;
    }

    public AnswerBot selectTypeAugury(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        stateDao.setSelectAugury(callbackData, chatId);
        Subs subs = subsDao.getService(stateDao.getSelectService(chatId));
        if(subs.getPrice()>0) {
            List<CallbackButton> listBtn = new ArrayList<>();
            listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
            listBtn.add(new CallbackButton(BUTTONS.BTN_USER_PAY));
            AnswerBot answer = setAnswer(update, STATE_BOT.USER_PAY, listBtn , 2);
            return answer;
        }
        else {
            List<CallbackButton> listBtn = new ArrayList<>();
            listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
            AnswerBot answer = setAnswer(update, STATE_BOT.USER_FINISH, listBtn , 1);
            cartomancy.start(chatId, subs.getId());
            return answer;
        }
    }

    public AnswerBot pay(Update update) {

        SendInvoice sendInvoice = SendInvoice.builder()
                .chatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()))
                .currency("RUB")
                .providerToken("1744374395:TEST:174bea79d9843e519613")
                .title("Тест")
                .startParameter("start_param")
                .description("Тест")
                .payload("Тест")
                .providerData("{\"save_card\": true }")
                .price(new LabeledPrice("Тест",10000))
                .build();
        AnswerBot answer = new AnswerBot();
        answer.setMessage(sendInvoice);
        return answer;
    }

    public AnswerBot createOrder(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK_TO_START));
        AnswerBot answer = setAnswer(update, STATE_BOT.USER_FINISH, listBtn , 2);
        return answer;
    }

    public AnswerBot back(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        STATE_BOT state = stateDao.getState(msg.getChatId());
        if(state.equals(STATE_BOT.USER_SERVICE_LIST))
            return start(update);
        else if(state.equals(STATE_BOT.USER_SERVICE_DETAILS))
            return getListService(update);
        else if(state.equals(STATE_BOT.USER_SELECT_CATEGORY))
            return getServiceDetails(update);
        else if(state.equals(STATE_BOT.USER_FINISH))
            return getListService(update);
        else if(state.equals(STATE_BOT.USER_PAY))
            return getCategoryPrediction(update);
        return null;
    }

    public AnswerBot backToStart(Update update) {
        return start(update);
    }
}
