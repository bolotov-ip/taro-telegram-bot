package com.everydaytarot.tarotelegrambot.model.augury;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "type_augury")
public class TypeAugury {
    @Id
    String augury;

    public String getAugury() {
        return augury;
    }

    public void setAugury(String augury) {
        this.augury = augury;
    }
}
