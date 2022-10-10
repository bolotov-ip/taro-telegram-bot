package com.everydaytarot.tarotelegrambot.model.augury;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuguryId implements Serializable {
    @Column(nullable = false)
    String nameCard;

    @Column(nullable = false)
    String augury;

    public AuguryId() {}

    public AuguryId(String nameCard, String augury) {
        this.nameCard = nameCard;
        this.augury = augury;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuguryId that = (AuguryId) o;
        return Objects.equals(nameCard, that.nameCard) && Objects.equals(augury, that.augury);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameCard, augury);
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
}
