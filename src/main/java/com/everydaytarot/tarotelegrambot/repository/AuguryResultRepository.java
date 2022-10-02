package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.augury.AuguryResult;
import org.springframework.data.repository.CrudRepository;

public interface AuguryResultRepository extends CrudRepository<AuguryResult, String> {
}
