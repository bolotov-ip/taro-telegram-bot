package com.everydaytarot.tarotelegrambot.model;

import javax.persistence.*;

@Entity(name = "predictions_cartomancy")
public class PredictionCartomancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(nullable = false, name = "card")
    String card;

    @Column(nullable = false, name = "category")
    String category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
