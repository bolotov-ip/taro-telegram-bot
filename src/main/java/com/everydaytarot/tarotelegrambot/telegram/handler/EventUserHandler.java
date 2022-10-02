package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.dao.AuguryResultDao;
import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.model.service.excel.ExcelParser;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.view.MessageButtonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventUserHandler {

    @Autowired
    StateDao stateDao;

    @Autowired
    AuguryResultDao auguryResultDao;

    @Autowired
    BotConfig botConfig;

    @Autowired
    ExcelParser excelParser;

    public AnswerBot start(Update update) {
        AnswerBot answer = new AnswerBot();
        String chatId = "";
        if(update.hasCallbackQuery()) {
            Message message = update.getCallbackQuery().getMessage();
            chatId = String.valueOf(message.getChatId());
            long messageId = message.getMessageId();
            EditMessageText editMessage = new EditMessageText();
            editMessage.setChatId(chatId);
            editMessage.setMessageId((int)messageId);
            editMessage.setText(STATE_BOT.USER_MENU.getTextMessage());
            MessageButtonView.setMessageButton(editMessage, STATE_BOT.USER_MENU.toString());
            answer.setAnswer(editMessage);

        }
        else {
            Message message = update.getMessage();
            chatId = String.valueOf(message.getChatId());
            SendMessage sendMessage = new SendMessage(chatId, STATE_BOT.USER_MENU.getTextMessage());
            MessageButtonView.setMessageButton(sendMessage, STATE_BOT.USER_MENU.toString());
            answer.setAnswer(sendMessage);
        }

        stateDao.setState(STATE_BOT.USER_MENU, Long.valueOf(chatId));
        return answer;
    }

    public AnswerBot addFreeOrder(Update update) {

        return null;
    }

    public AnswerBot commandNotSupport(Update update) {
        SendMessage send = new SendMessage(String.valueOf(update.getMessage().getChatId()), "Команда не поддерживается");
        AnswerBot answer = new AnswerBot(send);
        return answer;
    }
}
