package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.StateBot;
import com.everydaytarot.tarotelegrambot.repository.StateBotRepository;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StateDao {

    @Autowired
    private StateBotRepository stateBotRepository;
    public void setState(STATE_BOT state, Long chatId) {

        Optional<StateBot> oldState = stateBotRepository.findById(chatId);
        if(oldState.isEmpty()) {
            StateBot newState = StateBot.createStateBot(chatId, state);
            stateBotRepository.save(newState);
        }
        else {
            oldState.get().setStateBot(state.toString());
            stateBotRepository.save(oldState.get());
        }
    }

    public STATE_BOT getState(Long chatId) {
        Optional<StateBot> state = stateBotRepository.findById(chatId);
        if(state.isPresent())
            return STATE_BOT.valueOf(state.get().getStateBot());
        else
            return STATE_BOT.START;
    }
}
