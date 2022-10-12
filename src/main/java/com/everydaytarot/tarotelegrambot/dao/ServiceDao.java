package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDao extends JpaRepository<Service, Long> {
}
