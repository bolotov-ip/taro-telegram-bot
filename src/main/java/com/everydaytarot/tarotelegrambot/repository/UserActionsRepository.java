package com.everydaytarot.tarotelegrambot.repository;

import com.everydaytarot.tarotelegrambot.model.UserActions;
import org.springframework.data.repository.CrudRepository;

public interface UserActionsRepository extends CrudRepository<UserActions, Long> {
}
