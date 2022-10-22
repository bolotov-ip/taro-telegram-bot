package com.everydaytarot.tarotelegrambot.model;

import javax.persistence.*;

@Entity(name = "predictions_card_day")
public class PredictionCardDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(nullable = false, name = "card")
    String card;

    @Column(name = "text")
    String text;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
