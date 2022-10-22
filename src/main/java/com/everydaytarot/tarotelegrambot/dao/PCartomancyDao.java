package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.PCartomancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PCartomancyDao extends JpaRepository<PCartomancy, Long> {

    @Query("SELECT p FROM predictions_cartomancy p")
    List<PCartomancy> getCategories();

    @Query("SELECT p FROM predictions_cartomancy p")
    List<PCartomancy> getCards();

    @Query("SELECT p FROM predictions_cartomancy p where p.card =:card and p.category =:category")
    Optional<PCartomancy> findPrediction(@Param("card") String card, @Param("category") String category);
}
