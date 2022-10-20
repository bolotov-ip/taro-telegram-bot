package com.everydaytarot.tarotelegrambot.telegram.event;


import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.service.PredictionManager;
import com.everydaytarot.tarotelegrambot.service.ServiceManager;
import com.everydaytarot.tarotelegrambot.model.Service;
import com.everydaytarot.tarotelegrambot.service.CartomancyManager;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
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
    CartomancyManager cartomancyManager;

    @Autowired
    ServiceManager serviceManager;

    @Autowired
    PredictionManager predictionManager;


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
        List<Service> activeServices = serviceManager.getActiveServices(serviceType);
        for(Service service : activeServices) {
            CallbackButton btn = new CallbackButton(service.getName());
            btn.setCallbackData(service.getId().toString());
            listBtn.add(btn);
        }
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        return setAnswer(update, STATE_BOT.USER_SERVICE_LIST, listBtn, 1);
    }

    public AnswerBot getServiceDetails(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        stateDao.setSelectService(Long.valueOf(callbackData), chatId);
        Service service = serviceManager.getService(chatId);
        String description = service.getDescription();
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
        List<String> listTypeAugury = predictionManager.getAllCategory(service_type);
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
        Service service = serviceManager.getService(stateDao.getSelectService(chatId));
        if(service.getPrice()>0) {
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
            cartomancyManager.start(chatId, service.getId());
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
