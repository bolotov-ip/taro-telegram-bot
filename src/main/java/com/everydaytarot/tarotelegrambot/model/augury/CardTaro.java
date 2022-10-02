package com.everydaytarot.tarotelegrambot.model.augury;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "card_taro")
public class CardTaro {
    @Id
    String nameCard;

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }
}
