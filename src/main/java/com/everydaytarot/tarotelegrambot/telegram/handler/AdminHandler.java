package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.constant.COMANDS;
import com.everydaytarot.tarotelegrambot.domain.AnswerBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminHandler implements Handler{

    @Autowired
    private EventHandler eventHandler;

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

            if(textMessage.equals(COMANDS.COMMAND_START.getText())) {
                return eventHandler.start(update);
            }
            else {
                SendMessage send = new SendMessage(String.valueOf(update.getMessage().getChatId()), "Команда не поддерживается");
                AnswerBot answer = new AnswerBot(send);
                return answer;
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
        }
        return null;
    }
}
