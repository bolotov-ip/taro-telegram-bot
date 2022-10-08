package com.everydaytarot.tarotelegrambot.model.service;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "user_use_service")
public class Order {

    @EmbeddedId
    OrderId orderId;

    Integer countUseDay;

    Date dayLastUse;

    Timestamp dayEndUse;

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public Date getDayLastUse() {
        return dayLastUse;
    }

    public void setDayLastUse(Date dayLastUse) {
        this.dayLastUse = dayLastUse;
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
