package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.dao.AuguryResultDao;
import com.everydaytarot.tarotelegrambot.service.excel.ExcelParser;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventUserHandler extends EventHandler{

    @Autowired
    AuguryResultDao auguryResultDao;

    @Autowired
    ExcelParser excelParser;

    public AnswerBot start(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_USER_MENU);
        listBtn.add(BUTTONS.BTN_USER_START_SERVICE);
        if(update.hasCallbackQuery()) {
            return setCommonCallbackAnswer(update, STATE_BOT.USER_START, listBtn, 2);
        }
        else {
            return setCommonCallbackAnswer(update, STATE_BOT.USER_START, listBtn, 2);
        }
    }

    public AnswerBot addFreeOrder(Update update) {

        return null;
    }
}
