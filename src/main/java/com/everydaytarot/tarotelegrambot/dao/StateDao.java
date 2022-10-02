package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.telegram.StateBotUser;
import com.everydaytarot.tarotelegrambot.repository.StateBotUserRepository;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StateDao {

    @Autowired
    private StateBotUserRepository stateBotUserRepository;

    public void setState(STATE_BOT state, Long chatId) {

        Optional<StateBotUser> oldState = stateBotUserRepository.findById(chatId);
        if(oldState.isEmpty()) {
            StateBotUser newState = StateBotUser.createStateBot(chatId, state);
            stateBotUserRepository.save(newState);
        }
        else {
            oldState.get().setStateBot(state.toString());
            stateBotUserRepository.save(oldState.get());
        }
    }

    public STATE_BOT getState(Long chatId) {
        Optional<StateBotUser> state = stateBotUserRepository.findById(chatId);
        if(state.isPresent())
            return STATE_BOT.valueOf(state.get().getStateBot());
        else
            return STATE_BOT.ADMIN_START;
    }
}
