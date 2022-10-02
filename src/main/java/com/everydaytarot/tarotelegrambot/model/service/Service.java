package com.everydaytarot.tarotelegrambot.model.service;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "service")
public class Service {
    @Id
    String name;

    String description;

    Integer countUseDay;

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

    public Integer getCountUseDay() {
        return countUseDay;
    }

    public void setCountUseDay(Integer countUseDay) {
        this.countUseDay = countUseDay;
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
