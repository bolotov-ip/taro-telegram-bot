package com.everydaytarot.tarotelegrambot.model;

import javax.persistence.*;

@Entity(name = "augury_result")
public class AuguryResult {


    @Id
    String idAuguryWithCard;

    String nameCard;

    String augury;

    String result;

    public String getIdAuguryWithCard() {
        return idAuguryWithCard;
    }

    public void setIdAuguryWithCard(String idAuguryWithCard) {
        this.idAuguryWithCard = idAuguryWithCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getAugury() {
        return augury;
    }

    public void setAugury(String augury) {
        this.augury = augury;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
