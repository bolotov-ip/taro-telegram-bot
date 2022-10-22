package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.PredictionCartomancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PredictionCartomancyDao extends JpaRepository<PredictionCartomancy, Long> {

    @Query("SELECT p FROM predictions_cartomancy p")
    List<PredictionCartomancy> getCategories();

    @Query("SELECT p FROM predictions_cartomancy p")
    List<PredictionCartomancy> getCards();

    @Query("SELECT p FROM predictions_cartomancy p where p.card =:card and p.category =:category")
    Optional<PredictionCartomancy> findPrediction(@Param("card") String card, @Param("category") String category);
}
