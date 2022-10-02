package com.everydaytarot.tarotelegrambot.model.telegram;

import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "state_bot")
public class StateBotUser {

    public static StateBotUser createStateBot(Long chatId, STATE_BOT state) {
        StateBotUser stateBotUser = new StateBotUser();
        stateBotUser.setChatId(chatId);
        stateBotUser.setStateBot(state.toString());
        return stateBotUser;
    }

    @Id
    Long chatId;

    String stateBot;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getStateBot() {
        return stateBot;
    }

    public void setStateBot(String stateBot) {
        this.stateBot = stateBot;
    }
}
