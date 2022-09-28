package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
