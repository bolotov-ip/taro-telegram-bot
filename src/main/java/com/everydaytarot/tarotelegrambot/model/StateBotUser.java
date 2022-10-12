package com.everydaytarot.tarotelegrambot.model;

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
        stateBotUser.setSelectService(0L);
        stateBotUser.setSelectShirt("");
        return stateBotUser;
    }

    @Id
    Long chatId;

    String stateBot;

    String selectAugury;

    Long selectService;

    String selectShirt;

    public String getSelectShirt() {
        return selectShirt;
    }

    public void setSelectShirt(String selectShirt) {
        this.selectShirt = selectShirt;
    }

    public Long getSelectService() {
        return selectService;
    }

    public void setSelectService(Long selectService) {
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
