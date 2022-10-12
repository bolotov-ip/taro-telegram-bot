package com.everydaytarot.tarotelegrambot.model;

import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "state_bot")
public class StateBot {

    public static StateBot createStateBot(Long chatId, STATE_BOT state) {
        StateBot stateBot = new StateBot();
        stateBot.setChatId(chatId);
        stateBot.setStateBot(state.toString());
        stateBot.setSelectAugury("");
        stateBot.setSelectService(0L);
        stateBot.setSelectShirt("");
        return stateBot;
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
