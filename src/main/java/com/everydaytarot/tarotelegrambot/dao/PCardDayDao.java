package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.PCardDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PCardDayDao extends JpaRepository<PCardDay, Long> {
}
