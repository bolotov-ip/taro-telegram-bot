package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.Prediction;
import com.everydaytarot.tarotelegrambot.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceDao extends JpaRepository<Service, Long> {

    @Query("SELECT s FROM service s where s.state='ACTIVE' and s.type=:type")
    List<Service> getActiveServices(@Param("type") String type);
}
