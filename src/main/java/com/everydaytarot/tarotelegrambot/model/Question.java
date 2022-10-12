package com.everydaytarot.tarotelegrambot.model;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "category_prediction")
    String categoryPrediction;

    @Column(name = "date")
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCategoryPrediction() {
        return categoryPrediction;
    }

    public void setCategoryPrediction(String categoryPrediction) {
        this.categoryPrediction = categoryPrediction;
    }
}
