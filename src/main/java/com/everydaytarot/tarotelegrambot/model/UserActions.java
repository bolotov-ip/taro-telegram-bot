package com.everydaytarot.tarotelegrambot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name="user_actions")
public class UserActions {

    public static UserActions createUserActions(Long chatId) {
        UserActions userActions = new UserActions();
        userActions.setChatId(chatId);
        return userActions;
    }

    @Id
    Long chatId;

    String latestComandUser;

    Timestamp dateLatestComandUser;

    String latestComandBot;

    Timestamp dateLatestComandBot;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getLatestComandUser() {
        return latestComandUser;
    }

    public void setLatestComandUser(String latestComandUser) {
        this.latestComandUser = latestComandUser;
    }

    public Timestamp getDateLatestComandUser() {
        return dateLatestComandUser;
    }

    public void setDateLatestComandUser(Timestamp dateLatestComandUser) {
        this.dateLatestComandUser = dateLatestComandUser;
    }

    public String getLatestComandBot() {
        return latestComandBot;
    }

    public void setLatestComandBot(String latestComandBot) {
        this.latestComandBot = latestComandBot;
    }

    public Timestamp getDateLatestComandBot() {
        return dateLatestComandBot;
    }

    public void setDateLatestComandBot(Timestamp dateLatestComandBot) {
        this.dateLatestComandBot = dateLatestComandBot;
    }
}
