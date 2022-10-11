package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.dao.UserDao;
import com.everydaytarot.tarotelegrambot.model.service.Service;
import com.everydaytarot.tarotelegrambot.business.CartomancyManager;
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
    UserDao userDao;

    public AnswerBot start(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_USER_MENU));
        listBtn.add(new CallbackButton(BUTTONS.BTN_USER_MENU_SERVICE));
        return setAnswer(update, STATE_BOT.USER_START, listBtn, 2);
    }

    public AnswerBot getListService(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        List<String> listNamesService = cartomancyManager.getListServiceName();
        for(String nameService : listNamesService)
            listBtn.add(new CallbackButton(nameService));
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        return setAnswer(update, STATE_BOT.USER_SERVICE_LIST, listBtn, 1);
    }

    public AnswerBot getServiceDetails(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        stateDao.setSelectService(callbackData, update.getCallbackQuery().getMessage().getChatId());
        Service service = cartomancyManager.getService(callbackData);
        String description = service.getDescription();
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        listBtn.add(new CallbackButton(BUTTONS.BTN_USER_START_SERVICE));
        AnswerBot answer = setAnswer(update, STATE_BOT.USER_SERVICE_DETAILS, listBtn, 2);
        ((EditMessageText) answer.getMessage()).setText(description);
        return answer;
    }

    public AnswerBot getTypeAugury(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        List<String> listTypeAugury = cartomancyManager.getTypesAugury();
        List<CallbackButton> listBtn = new ArrayList<>();
        for(String type : listTypeAugury) {
            listBtn.add(new CallbackButton(type));
        }
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        AnswerBot answer = setAnswer(update, STATE_BOT.USER_SELECT_CATEGORY, listBtn, 2);
        return answer;
    }

    public AnswerBot selectTypeAugury(Update update, TelegramBot bot) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        stateDao.setSelectAugury(callbackData, chatId);
        Service service = cartomancyManager.getService(stateDao.getSelectService(chatId));
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
            cartomancyManager.startService(String.valueOf(chatId), service.getName(), bot);
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
            return getTypeAugury(update);
        return null;
    }

    public AnswerBot backToStart(Update update) {
        return start(update);
    }
}
