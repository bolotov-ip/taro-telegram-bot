package com.everydaytarot.tarotelegrambot.model;

import javax.persistence.*;

@Entity(name = "service")
public class Subs {

    public enum State {ACTIVE, NONACTIVE}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;

    @Column(name="name")
    String name;

    @Column(name="description")
    String description;

    @Column(name="count_use")
    Integer countUse;

    @Column(name="max_use")
    Integer maxUse;

    @Column(name="count_day")
    Integer countDay;

    @Column(name="price")
    Long price;

    @Column(name="state")
    String state = State.ACTIVE.toString();

    @Column(name="type")
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public Integer getMaxUse() {
        return maxUse;
    }

    public void setMaxUse(Integer maxUse) {
        this.maxUse = maxUse;
    }
}
