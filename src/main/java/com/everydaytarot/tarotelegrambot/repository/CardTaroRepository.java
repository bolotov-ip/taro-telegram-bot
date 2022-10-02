package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.augury.CardTaro;
import org.springframework.data.repository.CrudRepository;

public interface CardTaroRepository extends CrudRepository<CardTaro, String> {
}
