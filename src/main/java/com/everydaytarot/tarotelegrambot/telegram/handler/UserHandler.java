package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.COMMANDS;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        if (update.hasMessage() && update.getMessage().hasText()) {

            String textMessage = update.getMessage().getText();

            if(textMessage.equals(COMMANDS.COMMAND_START.getText())) {
                return eventUserHandler.start(update);
            }
            else {
                return eventUserHandler.commandNotSupport(update);
            }

        }
        else {
            return eventUserHandler.commandNotSupport(update);
        }
    }
}
