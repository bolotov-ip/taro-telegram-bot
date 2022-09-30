package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.AuguryResult;
import org.springframework.data.repository.CrudRepository;

public interface AuguryResultRepository extends CrudRepository<AuguryResult, String> {
}
