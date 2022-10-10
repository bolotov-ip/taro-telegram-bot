package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.constant.COMMANDS;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UserHandler implements Handler{

    @Autowired
    private EventUserHandler eventUserHandler;

    @Autowired
    private StateDao stateDao;

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private TelegramBot bot;

    private Update update;

    public void setBot(TelegramBot bot) {
        this.bot = bot;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    @Override
    public AnswerBot run() {
        if(update.hasMessage() && update.getMessage().hasSuccessfulPayment()) {
            return eventUserHandler.createOrder(update);
        }
        else if (update.hasMessage() && update.getMessage().hasText()) {

            String textMessage = update.getMessage().getText();

            if(textMessage.equals(COMMANDS.COMMAND_START.getText())) {
                return eventUserHandler.start(update);
            }
            else {
                return eventUserHandler.commandNotSupport(update);
            }

        }
        else if(update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();

            if(callbackData.equals(BUTTONS.BTN_USER_MENU_SERVICE.toString())) {
                return eventUserHandler.getListService(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_BACK.toString())) {
                return eventUserHandler.back(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_USER_START_SERVICE.toString())) {
                return eventUserHandler.getTypeAugury(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_USER_PAY.toString())) {
                return eventUserHandler.pay(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_BACK_TO_START.toString())) {
                return eventUserHandler.backToStart(update);
            }

            STATE_BOT state = stateDao.getState(update.getCallbackQuery().getMessage().getChatId());
            if(state.equals(STATE_BOT.USER_SERVICE_LIST)){
                return eventUserHandler.getServiceDetails(update);
            }
            else if(state.equals(STATE_BOT.USER_SELECT_CATEGORY)){
                return eventUserHandler.selectTypeAugury(update, bot);
            }
            return null;
        }
        else {
            return eventUserHandler.commandNotSupport(update);
        }
    }
}
