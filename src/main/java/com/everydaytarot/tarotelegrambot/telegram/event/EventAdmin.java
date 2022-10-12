package com.everydaytarot.tarotelegrambot.telegram.event;

import com.everydaytarot.tarotelegrambot.service.PredictionManager;
import com.everydaytarot.tarotelegrambot.service.ServiceManager;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import com.everydaytarot.tarotelegrambot.telegram.domain.CallbackButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventAdmin extends Event {

    @Autowired
    PredictionManager predictionManager;

    @Autowired
    ServiceManager serviceManager;

    private final Logger log = LoggerFactory.getLogger(EventAdmin.class);

    public AnswerBot start(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_ORDER_BUTTON));
        listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_MENU));
        if(update.hasCallbackQuery()) {
            return setAnswer(update, STATE_BOT.ADMIN_START, listBtn, 2);
        }
        else {
            return setAnswer(update, STATE_BOT.ADMIN_START, listBtn, 2);
        }
    }

    public AnswerBot pressMenu(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_ADD_FILE));
        return setAnswer(update, STATE_BOT.ADMIN_MENU, listBtn, 2);
    }

    public AnswerBot pressAddFile(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_ADD_XLSX_SERVICE));
        listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_ADD_XLSX_AUGURY));
        listBtn.add(new CallbackButton(BUTTONS.BTN_BACK));
        return setAnswer(update, STATE_BOT.ADMIN_ADD_FILE_MENU, listBtn, 1);
    }

    public AnswerBot pressLoadServise(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_CANCEL));
        listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_DOWNOLAD_FILE));
        return setAnswer(update, STATE_BOT.INPUT_XLSX_SERVICE, listBtn, 1);
    }

    public AnswerBot pressLoadAugury(Update update) {
        List<CallbackButton> listBtn = new ArrayList<>();
        listBtn.add(new CallbackButton(BUTTONS.BTN_CANCEL));
        listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_DOWNOLAD_FILE));
        return setAnswer(update, STATE_BOT.INPUT_XLSX_AUGURY, listBtn, 1);
    }


    public AnswerBot pressSendFile(Update update, STATE_BOT state) {
        String catalog = "";
        if(state.equals(STATE_BOT.INPUT_XLSX_AUGURY))
            catalog = botConfig.getCatalogAugury();
        else if(state.equals(STATE_BOT.INPUT_XLSX_SERVICE))
            catalog = botConfig.getCatalogService();
        File file = null;
        new File(catalog).mkdirs();
        Path path= Paths.get(catalog);
        if(Files.exists(path))
            for (File myFile : new File(catalog).listFiles()) {
                if (myFile.isFile()) file = myFile;
                break;
            }
        if(file == null)
            return setAnswer(update, STATE_BOT.ADMIN_FILE_NOT_FOUND, null, 0);
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        InputFile inputFile = new InputFile(file);
        SendDocument document = new SendDocument(String.valueOf(chatId), inputFile);
        AnswerBot answer = new AnswerBot();
        answer.setDocument(document);
        return answer;
    }

    public AnswerBot pressBack(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        STATE_BOT state = stateManager.getState(msg.getChatId());
        if(state.equals(STATE_BOT.ADMIN_MENU))
            return start(update);
        else if(state.equals(STATE_BOT.INPUT_XLSX_AUGURY) || state.equals(STATE_BOT.INPUT_XLSX_SERVICE) ||  state.equals(STATE_BOT.INPUT_CARD))
            return pressAddFile(update);
        else if(state.equals(STATE_BOT.ADMIN_ADD_FILE_MENU))
            return pressMenu(update);
        return null;
    }

    public AnswerBot getFile(Update update, STATE_BOT state) {

        AnswerBot answer = null;

        String fileName = update.getMessage().getDocument().getFileName();
        String fileId = update.getMessage().getDocument().getFileId();

        String catalog = "";
        if(state.equals(STATE_BOT.INPUT_XLSX_AUGURY)) {
            catalog  = botConfig.getCatalogAugury();
        }
        else if(state.equals(STATE_BOT.INPUT_XLSX_SERVICE)) {
            catalog = botConfig.getCatalogService();
        }
        deleteFile(catalog);
        try {
            String path = uploadFile(fileName, fileId, catalog);
            List<CallbackButton> listBtn = new ArrayList<>();
            listBtn.add(new CallbackButton(BUTTONS.BTN_BACK_TO_START));
            answer = setAnswer(update, STATE_BOT.LOAD, listBtn, 2);
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(state.equals(STATE_BOT.INPUT_XLSX_AUGURY)) {
                        predictionManager.parseFileExcel(path);
                    } else if (state.equals(STATE_BOT.INPUT_XLSX_SERVICE)) {
                        serviceManager.parseFileExcel(path);
                    }

                }
            });
            th.start();

        }
        catch (IOException e) {
            log.error("File download error: " + e.getMessage());
            List<CallbackButton> listBtn = new ArrayList<>();
            listBtn.add(new CallbackButton(BUTTONS.BTN_BACK_TO_START));
            listBtn.add(new CallbackButton(BUTTONS.BTN_ADMIN_AGAIN_LOAD));
            answer = setAnswer(update, STATE_BOT.ERROR_LOAD, listBtn, 2);
        }
        return answer;
    }

}
