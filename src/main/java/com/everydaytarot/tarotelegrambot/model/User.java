package com.everydaytarot.tarotelegrambot.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "usersDataTable")
public class User {

    @Id
    private Long chatId;

    private String firstName;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
