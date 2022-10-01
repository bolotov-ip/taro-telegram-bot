package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.StateBotUser;
import org.springframework.data.repository.CrudRepository;

public interface StateBotUserRepository extends CrudRepository<StateBotUser, Long> {
}
