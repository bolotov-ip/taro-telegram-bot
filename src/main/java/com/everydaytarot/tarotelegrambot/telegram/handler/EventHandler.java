package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.telegram.view.MenuView;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventHandler {

    @Autowired
    StateDao stateDao;

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
            editMessage.setText(STATE_BOT.START.getTextMessage());
            MenuView.setMessageButton(editMessage, STATE_BOT.START.toString());
            answer.setAnswer(editMessage);

        }
        else {
            Message message = update.getMessage();
            chatId = String.valueOf(message.getChatId());
            SendMessage sendMessage = new SendMessage(chatId, STATE_BOT.START.getTextMessage());
            answer.setAnswer(sendMessage);
            MenuView.setMessageButton(sendMessage, STATE_BOT.START.toString());
            answer.setAnswer(sendMessage);
        }

        stateDao.setState(STATE_BOT.START, Long.valueOf(chatId));
        return answer;
    }

    public AnswerBot pressMenu(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = String.valueOf(msg.getChatId());
        long messageId = msg.getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setText(STATE_BOT.BTN_ADMIN_MENU.getTextMessage());
        editMessage.setMessageId((int)messageId);
        MenuView.setMessageButton(editMessage, STATE_BOT.BTN_ADMIN_MENU.toString());
        AnswerBot answer = new AnswerBot(editMessage);
        stateDao.setState(STATE_BOT.BTN_ADMIN_MENU, Long.valueOf(chatId));

        return answer;
    }

    public AnswerBot pressAddXLSX(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = String.valueOf(msg.getChatId());
        long messageId = msg.getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setText(STATE_BOT.INPUT_XLSX.getTextMessage());
        editMessage.setMessageId((int)messageId);
        MenuView.setMessageButton(editMessage, STATE_BOT.INPUT_XLSX.toString());
        AnswerBot answer = new AnswerBot(editMessage);
        stateDao.setState(STATE_BOT.INPUT_XLSX, Long.valueOf(chatId));

        return answer;
    }

    public AnswerBot pressBack(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        STATE_BOT state = stateDao.getState(msg.getChatId());
        if(state.equals(STATE_BOT.BTN_ADMIN_MENU))
            return start(update);
        else if(state.equals(STATE_BOT.INPUT_XLSX))
            return pressMenu(update);
        return null;
    }
}
