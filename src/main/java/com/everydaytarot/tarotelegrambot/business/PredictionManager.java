package com.everydaytarot.tarotelegrambot.business;

import com.everydaytarot.tarotelegrambot.dao.PredictionDao;
import com.everydaytarot.tarotelegrambot.model.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PredictionManager {

    @Autowired
    private PredictionDao predictionDao;



    public void saveAuguryResult(String card, String category, String result) {
        Optional<Prediction> auguryResult =  predictionDao.findPrediction(card, category);
        Prediction newPrediction;
        if(auguryResult.isEmpty())
            newPrediction = new Prediction();
        else
            newPrediction = auguryResult.get();
        newPrediction.setCard(card);
        newPrediction.setCategory(category);
        newPrediction.setText(result);
        predictionDao.save(newPrediction);
    }

    public List<String> getCardNames(){
        List<Prediction> predictions = predictionDao.getCards();
        Set<String> cards = new HashSet<>();
        for(Prediction prediction : predictions)
            cards.add(prediction.getCategory());
        return new ArrayList<>(cards);
    }

    public List<String> getAllCategory() {
        List<Prediction> predictions = predictionDao.getCategories();
        Set<String> categories = new HashSet<>();
        for(Prediction prediction : predictions)
            categories.add(prediction.getCategory());
        return new ArrayList<>(categories);
    }

    public void clearAuguryTables() {
        predictionDao.deleteAll();
    }

    public String getAugury(String card, String category) {
        Optional<Prediction> prediction =  predictionDao.findPrediction(card, category);
        if(prediction.isPresent())
            return prediction.get().getText();
        else
            return "";
    }

}
