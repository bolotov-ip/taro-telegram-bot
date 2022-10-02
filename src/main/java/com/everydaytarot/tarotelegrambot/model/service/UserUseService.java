package com.everydaytarot.tarotelegrambot.model.service;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "user_use_service")
public class UserUseService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    Long id;

    Long chatId;

    String nameService;

    Integer countUseDay;

    Timestamp dayLastUse;

    Timestamp dayEndUse;

    public Timestamp getDayLastUse() {
        return dayLastUse;
    }

    public void setDayLastUse(Timestamp dayLastUse) {
        this.dayLastUse = dayLastUse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public Integer getCountUseDay() {
        return countUseDay;
    }

    public void setCountUseDay(Integer countUseDay) {
        this.countUseDay = countUseDay;
    }

    public Timestamp getDayEndUse() {
        return dayEndUse;
    }

    public void setDayEndUse(Timestamp dayEndUse) {
        this.dayEndUse = dayEndUse;
    }
}
