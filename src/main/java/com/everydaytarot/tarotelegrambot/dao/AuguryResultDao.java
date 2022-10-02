package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.augury.AuguryResult;
import com.everydaytarot.tarotelegrambot.model.augury.CardTaro;
import com.everydaytarot.tarotelegrambot.model.augury.TypeAugury;
import com.everydaytarot.tarotelegrambot.repository.AuguryResultRepository;
import com.everydaytarot.tarotelegrambot.repository.CardTaroRepository;
import com.everydaytarot.tarotelegrambot.repository.TypeAuguryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuguryResultDao {

    @Autowired
    private AuguryResultRepository auguryResultRepository;

    @Autowired
    private CardTaroRepository cardTaroRepository;

    @Autowired
    private TypeAuguryRepository typeAuguryRepository;

    public void saveTypeAugury(String augury) {
       Optional<TypeAugury> typeAugury =  typeAuguryRepository.findById(augury);
       TypeAugury newTypeAugury;
       if(typeAugury.isEmpty())
           newTypeAugury = new TypeAugury();
       else
           newTypeAugury = typeAugury.get();

        newTypeAugury.setAugury(augury);
        typeAuguryRepository.save(newTypeAugury);
    }

    public void saveAuguryResult(String card, String augury, String result) {
        Optional<AuguryResult> auguryResult =  auguryResultRepository.findById(augury+card);
        AuguryResult newAuguryResult;
        if(auguryResult.isEmpty())
            newAuguryResult = new AuguryResult();
        else
            newAuguryResult = auguryResult.get();
        newAuguryResult.setIdAuguryWithCard(augury+card);
        newAuguryResult.setAugury(augury);
        newAuguryResult.setNameCard(card);
        newAuguryResult.setResult(result);
        auguryResultRepository.save(newAuguryResult);
    }

    public void saveCard(String card) {
        Optional<CardTaro> cardTaro =  cardTaroRepository.findById(card);
        CardTaro newCardTaro;
        if(cardTaro.isEmpty())
            newCardTaro = new CardTaro();
        else
            newCardTaro = cardTaro.get();
        newCardTaro.setNameCard(card);
        cardTaroRepository.save(newCardTaro);
    }

    public void clearAuguryTables() {
        auguryResultRepository.deleteAll();
        cardTaroRepository.deleteAll();
        typeAuguryRepository.deleteAll();
    }
}
