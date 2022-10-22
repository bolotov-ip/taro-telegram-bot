package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.PredictionCardDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionCardDayDao extends JpaRepository<PredictionCardDay, Long> {
}
