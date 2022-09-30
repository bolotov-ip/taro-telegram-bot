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
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminHandler implements Handler{

    @Autowired
    private EventHandler eventHandler;

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
        if (update.hasMessage() && update.getMessage().hasText()) {

            String textMessage = update.getMessage().getText();

            if(textMessage.equals(COMMANDS.COMMAND_START.getText())) {
                return eventHandler.start(update);
            }
            else {
                return eventHandler.commandNotSupport(update);
            }

        } else if(update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();

            if(callbackData.equals(BUTTONS.BTN_ADMIN_MENU.toString())) {
                return eventHandler.pressMenu(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_ADMIN_ADD_XLSX.toString())) {
                return eventHandler.pressAddXLSX(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_BACK.toString())) {
                return eventHandler.pressBack(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_BACK_TO_START.toString())) {
                return eventHandler.start(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_AGAIN_LOAD.toString())) {
                return eventHandler.pressAddXLSX(update);
            }
            else if(callbackData.equals(BUTTONS.BTN_CANCEL.toString())) {
                return eventHandler.pressBack(update);
            }
        } else if(update.getMessage().hasDocument()) {
            STATE_BOT state = stateDao.getState(update.getMessage().getChatId());
            if(state.equals(STATE_BOT.INPUT_XLSX)) {
                return eventHandler.downloadExcel(update);
            }
        } else {
            return eventHandler.commandNotSupport(update);
        }
        return null;
    }
}
