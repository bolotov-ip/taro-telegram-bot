package com.everydaytarot.tarotelegrambot.model.augury;

import javax.persistence.*;

@Entity(name = "augury_result")
public class Augury {


    @EmbeddedId
    AuguryId auguryId;

    String auguryText;

    public AuguryId getAuguryId() {
        return auguryId;
    }

    public void setAuguryId(AuguryId auguryId) {
        this.auguryId = auguryId;
    }

    public String getAuguryText() {
        return auguryText;
    }

    public void setAuguryText(String auguryText) {
        this.auguryText = auguryText;
    }
}
