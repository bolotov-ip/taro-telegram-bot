package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.StateBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateDao extends JpaRepository<StateBot, Long> {

    default void setState(STATE_BOT state, Long chatId) {

        Optional<StateBot> oldState = findById(chatId);
        if(oldState.isEmpty()) {
            StateBot newState = StateBot.createStateBot(chatId, state);
            save(newState);
        }
        else {
            oldState.get().setStateBot(state.toString());
            save(oldState.get());
        }
    }

    default STATE_BOT getState(Long chatId) {
        Optional<StateBot> state = findById(chatId);
        if(state.isPresent())
            return STATE_BOT.valueOf(state.get().getStateBot());
        else
            return STATE_BOT.ADMIN_START;
    }

    default String getShirt(Long chatId) {
        Optional<StateBot> state = findById(chatId);
        if(state.isPresent())
            return state.get().getSelectShirt();
        else
            return "";
    }

    default void setSelectAugury(String augury, Long chatId) {
        Optional<StateBot> oldAugury = findById(chatId);
        if(oldAugury.isPresent()){
            oldAugury.get().setSelectAugury(augury);
            save(oldAugury.get());
        }
        else{
            StateBot newState = StateBot.createStateBot(chatId, STATE_BOT.USER_START);
            newState.setSelectAugury(augury);
            save(newState);
        }
    }

    default String getSelectAugury(Long chatId) {
        Optional<StateBot> state = findById(chatId);
        if(state.isPresent())
            return state.get().getSelectAugury();
        else
            return "";
    }

    default void setSelectService(Long service, Long chatId) {
        Optional<StateBot> oldAugury = findById(chatId);
        if(oldAugury.isPresent()) {
            oldAugury.get().setSelectService(service);
            save(oldAugury.get());
        }
        else{
            StateBot newState = StateBot.createStateBot(chatId, STATE_BOT.USER_START);
            newState.setSelectService(service);
            save(newState);
        }
    }

    default Long getSelectService(Long chatId) {
        Optional<StateBot> state = findById(chatId);
        if(state.isPresent())
            return state.get().getSelectService();
        else
            return 0L;
    }
}
