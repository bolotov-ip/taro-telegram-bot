package com.everydaytarot.tarotelegrambot.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity(name = "orders")
public class Order {

    public enum StateOrder {ACTIVE, COMPLETE}

    public static Order createOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setServiceId(rs.getLong("service_id"));
        order.setChatId(rs.getLong("chat_id"));
        return order;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "chat_id")
    Long chatId;

    @Column(name = "service_id")
    Long serviceId;

    @Column(name = "state")
    String state = StateOrder.ACTIVE.toString();

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

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
