package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.model.service.Service;
import com.everydaytarot.tarotelegrambot.service.card_day.CardOfTheDayService;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.domain.CallbackButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventUserHandler extends EventHandler{

    @Autowired
    CardOfTheDayService cardOfTheDayService;

    public AnswerBot start(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_USER_MENU));
        listBtn.add(new CallbackButton(BUTTONS.BTN_USER_MENU_SERVICE));
        return setAnswer(update, STATE_BOT.USER_START, listBtn, 2);
    }

    public AnswerBot getListService(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        List<String> listNamesService = cardOfTheDayService.getListServiceName();
        for(String nameService : listNamesService)
            listBtn.add(new CallbackButton(nameService));
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        return setAnswer(update, STATE_BOT.USER_SERVICE_LIST, listBtn, 1);
    }

    public AnswerBot getServiceDetails(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Service service = cardOfTheDayService.getService(callbackData);
        String description = service.getDescription();
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        listBtn.add(new CallbackButton(BUTTONS.BTN_USER_START_SERVICE));
        AnswerBot answer = setAnswer(update, STATE_BOT.USER_SERVICE_DETAILS, listBtn, 2);
        ((EditMessageText) answer.getMessage()).setText(description);
        return answer;
    }

    public AnswerBot startService(Update update) {
        return null;
    }

    public AnswerBot back(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        STATE_BOT state = stateDao.getState(msg.getChatId());
        if(state.equals(STATE_BOT.USER_SERVICE_LIST))
            return start(update);
        else if(state.equals(STATE_BOT.USER_SERVICE_DETAILS))
            return getListService(update);
        return null;
    }

}
