package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryDao extends JpaRepository<Query, Long> {
}
