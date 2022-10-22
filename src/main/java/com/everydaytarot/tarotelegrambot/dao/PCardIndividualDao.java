package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.model.PCardDay;
import com.everydaytarot.tarotelegrambot.model.PCardIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface PCardIndividualDao extends JpaRepository<PCardIndividual, Long> {

    @Query("SELECT p FROM predictions_card_individual p")
    List<PCardIndividual> getCategories();

    @Query("SELECT p FROM predictions_card_individual p")
    List<PCardIndividual> getCards();

    @Query("SELECT p FROM predictions_card_individual p where p.card =:card and p.category =:category")
    Optional<PCardIndividual> findPrediction(@Param("card") String card, @Param("category") String category);

    public default void savePrediction(String card, String category, String text) {
        Optional<PCardIndividual> auguryResult =  findPrediction(card, category);
        PCardIndividual newPCardIndividual;
        if(auguryResult.isEmpty())
            newPCardIndividual = new PCardIndividual();
        else
            newPCardIndividual = auguryResult.get();
        newPCardIndividual.setCard(card);
        newPCardIndividual.setCategory(category);
        newPCardIndividual.setText(text);
        save(newPCardIndividual);
    }

    public default List<String> getCardNames(){
        List<PCardIndividual> predictionCartomancies = getCards();
        Set<String> cards = new HashSet<>();
        for(PCardIndividual pCardIndividual : predictionCartomancies)
            cards.add(pCardIndividual.getCategory());
        return new ArrayList<>(cards);
    }

    public default List<String> getAllCategory(SERVICE_TYPE service_type) {
        List<PCardIndividual> predictionCartomancies = getCategories();
        Set<String> categories = new HashSet<>();
        for(PCardIndividual pCardIndividual : predictionCartomancies)
            categories.add(pCardIndividual.getCategory());
        return new ArrayList<>(categories);
    }

    public default void clearAuguryTables() {
        deleteAll();
    }

    public default String getAugury(String card, String category) {
        Optional<PCardIndividual> prediction =  findPrediction(card, category);
        if(prediction.isPresent())
            return prediction.get().getText();
        else
            return "";
    }

    public default void addPCardIndividual(List<String[]> rows) {
        List<PCardIndividual> pCardIndividualList = new ArrayList<>();
        for(String[] row : rows) {
            PCardIndividual pCardIndividual = new PCardIndividual();
            pCardIndividual.setCategory(row[0]);
            pCardIndividual.setCard(row[1]);
            pCardIndividual.setText(row[2]);
            pCardIndividualList.add(pCardIndividual);
        }
        saveAll(pCardIndividualList);
    }
}
