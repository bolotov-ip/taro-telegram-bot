package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.augury.Augury;
import com.everydaytarot.tarotelegrambot.model.augury.AuguryId;
import com.everydaytarot.tarotelegrambot.model.augury.CardTaro;
import com.everydaytarot.tarotelegrambot.model.augury.TypeAugury;
import com.everydaytarot.tarotelegrambot.repository.AuguryResultRepository;
import com.everydaytarot.tarotelegrambot.repository.CardTaroRepository;
import com.everydaytarot.tarotelegrambot.repository.TypeAuguryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
        AuguryId auguryId = new AuguryId(card, augury);
        Optional<Augury> auguryResult =  auguryResultRepository.findById(auguryId);
        Augury newAugury;
        if(auguryResult.isEmpty())
            newAugury = new Augury();
        else
            newAugury = auguryResult.get();
        newAugury.setAuguryId(auguryId);
        newAugury.setAuguryText(result);
        auguryResultRepository.save(newAugury);
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

    public List<String> getCardNames(){
        List<String> cardNames = new ArrayList<>();
        Iterable<CardTaro> iterable = cardTaroRepository.findAll();
        Iterator<CardTaro> iterator = iterable.iterator();
        while(iterator.hasNext()) {
            cardNames.add(iterator.next().getNameCard());
        }
        return cardNames;
    }

    public void clearAuguryTables() {
        auguryResultRepository.deleteAll();
        cardTaroRepository.deleteAll();
        typeAuguryRepository.deleteAll();
    }

    public String getAugury(String cardName, String category) {
        AuguryId auguryId = new AuguryId(cardName, category);
        Optional<Augury> result = auguryResultRepository.findById(auguryId);
        if(result.isPresent())
            return result.get().getAuguryText();
        else
            return "";
    }

    public List<String> getAllCategory() {
        List<String> result = new ArrayList<>();
        Iterable<TypeAugury> iterable = typeAuguryRepository.findAll();
        Iterator<TypeAugury> iterator = iterable.iterator();
        while(iterator.hasNext()){
            result.add(iterator.next().getAugury());
        }
        return result;
    }
}
