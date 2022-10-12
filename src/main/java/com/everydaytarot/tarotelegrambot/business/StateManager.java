package com.everydaytarot.tarotelegrambot.business;

import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.model.StateBotUser;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StateManager {

    @Autowired
    private StateDao stateDao;

    public void setState(STATE_BOT state, Long chatId) {

        Optional<StateBotUser> oldState = stateDao.findById(chatId);
        if(oldState.isEmpty()) {
            StateBotUser newState = StateBotUser.createStateBot(chatId, state);
            stateDao.save(newState);
        }
        else {
            oldState.get().setStateBot(state.toString());
            stateDao.save(oldState.get());
        }
    }

    public STATE_BOT getState(Long chatId) {
        Optional<StateBotUser> state = stateDao.findById(chatId);
        if(state.isPresent())
            return STATE_BOT.valueOf(state.get().getStateBot());
        else
            return STATE_BOT.ADMIN_START;
    }

    public String getShirt(Long chatId) {
        Optional<StateBotUser> state = stateDao.findById(chatId);
        if(state.isPresent())
            return state.get().getSelectShirt();
        else
            return "";
    }

    public void setSelectAugury(String augury, Long chatId) {
        Optional<StateBotUser> oldAugury = stateDao.findById(chatId);
        if(oldAugury.isPresent()){
            oldAugury.get().setSelectAugury(augury);
            stateDao.save(oldAugury.get());
        }
        else{
            StateBotUser newState = StateBotUser.createStateBot(chatId, STATE_BOT.USER_START);
            newState.setSelectService(augury);
            stateDao.save(newState);
        }
    }

    public String getSelectAugury(Long chatId) {
        Optional<StateBotUser> state = stateDao.findById(chatId);
        if(state.isPresent())
            return state.get().getSelectAugury();
        else
            return "";
    }

    public void setSelectService(Long service, Long chatId) {
        Optional<StateBotUser> oldAugury = stateDao.findById(chatId);
        if(oldAugury.isPresent()) {
            oldAugury.get().setSelectService(service);
            stateDao.save(oldAugury.get());
        }
        else{
            StateBotUser newState = StateBotUser.createStateBot(chatId, STATE_BOT.USER_START);
            newState.setSelectService(service);
            stateDao.save(newState);
        }
    }

    public Long getSelectService(Long chatId) {
        Optional<StateBotUser> state = stateDao.findById(chatId);
        if(state.isPresent())
            return state.get().getSelectService();
        else
            return 0;
    }
}
