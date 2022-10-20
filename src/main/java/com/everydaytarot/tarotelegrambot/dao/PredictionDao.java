package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PredictionDao extends JpaRepository<Prediction, Long> {

    @Query("SELECT p FROM predictions p")
    List<Prediction> getCategories();

    @Query("SELECT p FROM predictions p")
    List<Prediction> getCards();

    @Query("SELECT p FROM predictions p where p.card =:card and p.category =:category")
    Optional<Prediction> findPrediction(@Param("card") String card, @Param("category") String category);
}
