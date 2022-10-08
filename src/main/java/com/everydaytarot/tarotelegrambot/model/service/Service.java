package com.everydaytarot.tarotelegrambot.model.service;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "service")
public class Service {
    @Id
    String name;

    String description;

    Integer countUse;

    Integer countDay;

    Long price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCountUse() {
        return countUse;
    }

    public void setCountUse(Integer countUse) {
        this.countUse = countUse;
    }

    public Integer getCountDay() {
        return countDay;
    }

    public void setCountDay(Integer countDay) {
        this.countDay = countDay;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
