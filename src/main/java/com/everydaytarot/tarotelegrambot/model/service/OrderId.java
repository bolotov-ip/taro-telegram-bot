package com.everydaytarot.tarotelegrambot.model.service;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderId implements Serializable {
    @Column(nullable = false)
    Long chatId;

    @Column(nullable = false)
    String nameService;

    public OrderId() {}

    public OrderId(Long chatId, String nameService) {
        this.chatId = chatId;
        this.nameService = nameService;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId that = (OrderId) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(nameService, that.nameService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, nameService);
    }
}
