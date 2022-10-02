package com.everydaytarot.tarotelegrambot.model.service;

import org.apache.poi.ss.formula.functions.T;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "user_limited")
public class UserUseService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    Long id;

    Long chatId;

    String nameService;

    Integer countUseDay;

    Timestamp endOfUseDay;

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

    public Timestamp getEndOfUseDay() {
        return endOfUseDay;
    }

    public void setEndOfUseDay(Timestamp endOfUseDay) {
        this.endOfUseDay = endOfUseDay;
    }
}
