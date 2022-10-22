package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.PCardIndividual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PCardIndividualDao extends JpaRepository<PCardIndividual, Long> {

    @Query("SELECT p FROM predictions_card_individual p")
    List<PCardIndividual> getCategories();

    @Query("SELECT p FROM predictions_card_individual p")
    List<PCardIndividual> getCards();

    @Query("SELECT p FROM predictions_card_individual p where p.card =:card and p.category =:category")
    Optional<PCardIndividual> findPrediction(@Param("card") String card, @Param("category") String category);
}
