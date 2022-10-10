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
        stateBotUser.setSelectAugury("");
        stateBotUser.setSelectService("");
        return stateBotUser;
    }

    @Id
    Long chatId;

    String stateBot;

    String selectAugury;

    String selectService;

    public String getSelectService() {
        return selectService;
    }

    public void setSelectService(String selectService) {
        this.selectService = selectService;
    }

    public String getSelectAugury() {
        return selectAugury;
    }

    public void setSelectAugury(String selectAugury) {
        this.selectAugury = selectAugury;
    }

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
