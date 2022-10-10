package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.augury.Augury;
import com.everydaytarot.tarotelegrambot.model.augury.AuguryId;
import org.springframework.data.repository.CrudRepository;

public interface AuguryResultRepository extends CrudRepository<Augury, AuguryId> {
}
