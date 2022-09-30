package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.view.MenuView;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventHandler {

    public static AnswerBot start(Update update) {

        String textMessage = update.getMessage().getText();
        String chatId = String.valueOf(update.getMessage().getChatId());
        AnswerBot answer = new AnswerBot(new SendMessage(chatId, "Добро пожаловать"));
        MenuView.setMessageButton((SendMessage) answer.getAnswer());

        return answer;
    }

    public static AnswerBot pressMenu(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = String.valueOf(msg.getChatId());
        long messageId = msg.getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setText("Меню");
        editMessage.setMessageId((int)messageId);
        MenuView.setEditMessageButton(editMessage);
        AnswerBot answer = new AnswerBot(editMessage);

        return answer;
    }
}
