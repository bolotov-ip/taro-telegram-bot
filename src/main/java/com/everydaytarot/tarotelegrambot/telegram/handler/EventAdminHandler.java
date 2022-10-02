package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.dao.AuguryResultDao;
import com.everydaytarot.tarotelegrambot.service.excel.ExcelParser;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
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
public class EventAdminHandler extends EventHandler{

    @Autowired
    AuguryResultDao auguryResultDao;

    @Autowired
    ExcelParser excelParser;

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    public AnswerBot start(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_ADMIN_ORDER_BUTTON);
        listBtn.add(BUTTONS.BTN_ADMIN_MENU);
        if(update.hasCallbackQuery()) {
            return setCommonCallbackAnswer(update, STATE_BOT.ADMIN_START, listBtn, 2);
        }
        else {
            return setCommonAnswer(update, STATE_BOT.ADMIN_START, listBtn, 2);
        }
    }

    public AnswerBot pressMenu(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_BACK);
        listBtn.add(BUTTONS.BTN_ADMIN_ADD_FILE);
        listBtn.add(BUTTONS.BTN_ADMIN_SERVICE);
        return setCommonCallbackAnswer(update, STATE_BOT.ADMIN_MENU, listBtn, 2);
    }

    public AnswerBot pressAddFile(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_ADMIN_ADD_XLSX_SERVICE);
        listBtn.add(BUTTONS.BTN_ADMIN_ADD_XLSX_AUGURY);
        listBtn.add(BUTTONS.BTN_ADMIN_ADD_CARD_PHOTO);
        listBtn.add(BUTTONS.BTN_BACK);
        return setCommonCallbackAnswer(update, STATE_BOT.ADMIN_ADD_FILE_MENU, listBtn, 1);
    }

    public AnswerBot pressLoadServise(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_CANCEL);
        listBtn.add(BUTTONS.BTN_ADMIN_DOWNOLAD_FILE);
        return setCommonCallbackAnswer(update, STATE_BOT.INPUT_XLSX_SERVICE, listBtn, 1);
    }

    public AnswerBot pressLoadAugury(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_CANCEL);
        listBtn.add(BUTTONS.BTN_ADMIN_DOWNOLAD_FILE);
        return setCommonCallbackAnswer(update, STATE_BOT.INPUT_XLSX_AUGURY, listBtn, 1);
    }

    public AnswerBot pressLoadCard(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_CANCEL);
        listBtn.add(BUTTONS.BTN_ADMIN_DOWNOLAD_FILE);
        return setCommonCallbackAnswer(update, STATE_BOT.INPUT_CARD, listBtn, 1);
    }

    public AnswerBot pressDownloadFile(Update update, STATE_BOT state) {
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
            return setCommonCallbackAnswer(update, STATE_BOT.ADMIN_FILE_NOT_FOUND, null, 0);
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        InputFile inputFile = new InputFile(file);
        SendDocument document = new SendDocument(String.valueOf(chatId), inputFile);
        AnswerBot answer = new AnswerBot();
        answer.setDocument(document);
        return answer;
    }

    public AnswerBot pressBack(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        STATE_BOT state = stateDao.getState(msg.getChatId());
        if(state.equals(STATE_BOT.ADMIN_MENU))
            return start(update);
        else if(state.equals(STATE_BOT.INPUT_XLSX_AUGURY) || state.equals(STATE_BOT.INPUT_XLSX_SERVICE) ||  state.equals(STATE_BOT.INPUT_CARD))
            return pressAddFile(update);
        else if(state.equals(STATE_BOT.ADMIN_LIST_SERVICE) || state.equals(STATE_BOT.ADMIN_ADD_FILE_MENU))
            return pressMenu(update);
        return null;
    }

    public AnswerBot downloadFile(Update update, STATE_BOT state) {


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
        else if(state.equals(STATE_BOT.INPUT_CARD)) {
            catalog = botConfig.getCatalogCard();
        }
        excelParser.deleteFileXlsx(catalog);
        try {
            String path = uploadFile(fileName, fileId, catalog);
            List<BUTTONS> listBtn = new ArrayList<>();
            listBtn.add(BUTTONS.BTN_BACK_TO_START);
            answer = setCommonAnswer(update, STATE_BOT.LOAD, listBtn, 2);
            if(state.equals(STATE_BOT.INPUT_XLSX_AUGURY)) {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        auguryResultDao.clearAuguryTables();
                        excelParser.parserXlsx(path);
                    }
                });
                th.start();
            }
        }
        catch (IOException e) {
            log.error("File download error: " + e.getMessage());
            List<BUTTONS> listBtn = new ArrayList<>();
            listBtn.add(BUTTONS.BTN_BACK_TO_START);
            listBtn.add(BUTTONS.BTN_ADMIN_AGAIN_LOAD);
            answer = setCommonCallbackAnswer(update, STATE_BOT.ERROR_LOAD, listBtn, 2);
        }
        return answer;
    }

    public AnswerBot pressService(Update update) {
        List<BUTTONS> listBtn = new ArrayList<>();
        listBtn.add(BUTTONS.BTN_BACK);
        listBtn.add(BUTTONS.BTN_ADMIN_SHOW_SERVICE);
        listBtn.add(BUTTONS.BTN_ADMIN_ADD_SERVICE);
        return setCommonCallbackAnswer(update, STATE_BOT.ADMIN_LIST_SERVICE, listBtn, 2);
    }

    public AnswerBot showService(Update update) {
        return null;
    }
}
