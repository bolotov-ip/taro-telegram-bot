package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.AuguryResult;
import com.everydaytarot.tarotelegrambot.model.CardTaro;
import com.everydaytarot.tarotelegrambot.model.TypeAugury;
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
       if(typeAugury.isEmpty()) {
           TypeAugury newTypeAugury = new TypeAugury();
           newTypeAugury.setAugury(augury);
           typeAuguryRepository.save(newTypeAugury);
       }
    }

    public void saveAuguryResult(String card, String augury, String result) {
        Optional<AuguryResult> typeAugury =  auguryResultRepository.findById(augury+card);
        if(typeAugury.isEmpty()) {
            AuguryResult newAuguryResult = new AuguryResult();
            newAuguryResult.setIdAuguryWithCard(augury+card);
            newAuguryResult.setAugury(augury);
            newAuguryResult.setNameCard(card);
            newAuguryResult.setResult(result);
            auguryResultRepository.save(newAuguryResult);
        }
    }

    public void saveCard(String card) {
        Optional<CardTaro> typeAugury =  cardTaroRepository.findById(card);
        if(typeAugury.isEmpty()) {
            CardTaro newCardTaro = new CardTaro();
            newCardTaro.setNameCard(card);
            cardTaroRepository.save(newCardTaro);
        }
    }
}
