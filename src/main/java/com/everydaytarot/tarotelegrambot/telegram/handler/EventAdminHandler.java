package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.dao.AuguryResultDao;
import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.model.service.excel.ExcelParser;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import com.everydaytarot.tarotelegrambot.telegram.view.MessageButtonView;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Component
public class EventAdminHandler {

    @Autowired
    StateDao stateDao;

    @Autowired
    AuguryResultDao auguryResultDao;

    @Autowired
    BotConfig botConfig;

    @Autowired
    ExcelParser excelParser;

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    public AnswerBot start(Update update) {
        if(update.hasCallbackQuery()) {
            return setCommonCallbackAnswer(update, STATE_BOT.START);
        }
        else {
            return setCommonAnswer(update, STATE_BOT.START);
        }
    }

    public AnswerBot pressMenu(Update update) {
        return setCommonCallbackAnswer(update, STATE_BOT.ADMIN_MENU);
    }

    public AnswerBot pressAddXLSX(Update update) {
        return setCommonCallbackAnswer(update, STATE_BOT.INPUT_XLSX);
    }

    public AnswerBot pressBack(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        STATE_BOT state = stateDao.getState(msg.getChatId());
        if(state.equals(STATE_BOT.ADMIN_MENU))
            return start(update);
        else if(state.equals(STATE_BOT.INPUT_XLSX))
            return pressMenu(update);
        else if(state.equals(STATE_BOT.ADMIN_SERVICE_MENU))
            return pressMenu(update);
        return null;
    }

    public AnswerBot downloadExcel(Update update) {
        excelParser.deleteFileXlsx();

        AnswerBot answer = null;

        String fileName = update.getMessage().getDocument().getFileName();
        String fileId = update.getMessage().getDocument().getFileId();
        try {
            uploadFile(fileName, fileId, botConfig.getCatalogXlsx());
            answer = setCommonCallbackAnswer(update, STATE_BOT.LOAD);
        }
        catch (IOException e) {
            log.error("File download error: " + e.getMessage());
            answer = setCommonCallbackAnswer(update, STATE_BOT.ERROR_LOAD);
        }
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                auguryResultDao.clearAuguryTables();
                excelParser.parserXlsx(botConfig.getCatalogXlsx()+fileName);
            }
        });
        th.start();
        return answer;
    }

    public AnswerBot pressService(Update update) {
        return setCommonCallbackAnswer(update, STATE_BOT.ADMIN_SERVICE_MENU);
    }

    public AnswerBot commandNotSupport(Update update) {
        SendMessage send = new SendMessage(String.valueOf(update.getMessage().getChatId()), "Команда не поддерживается");
        AnswerBot answer = new AnswerBot(send);
        return answer;
    }

    public void uploadFile(String fileName, String fileId, String pathFile) throws IOException {
        URL url = new URL("https://api.telegram.org/bot"+botConfig.getToken()+"/getFile?file_id="+fileId);
        BufferedReader in = new BufferedReader(new InputStreamReader( url.openStream()));
        String res = in.readLine();
        JSONObject jresult = new JSONObject(res);
        JSONObject path = jresult.getJSONObject("result");
        String file_path = path.getString("file_path");
        URL downoload = new URL("https://api.telegram.org/file/bot" + botConfig.getToken() + "/" + file_path);
        FileOutputStream fos = new FileOutputStream(pathFile + fileName);
        ReadableByteChannel rbc = Channels.newChannel(downoload.openStream());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
        log.info("File " + fileName + " download");
    }

    private AnswerBot setCommonCallbackAnswer(Update update, STATE_BOT stateBot) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = String.valueOf(msg.getChatId());
        long messageId = msg.getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setText(stateBot.getTextMessage());
        editMessage.setMessageId((int)messageId);
        MessageButtonView.setMessageButton(editMessage, stateBot.toString());
        AnswerBot answer = new AnswerBot(editMessage);
        stateDao.setState(stateBot, Long.valueOf(chatId));

        return answer;
    }

    private AnswerBot setCommonAnswer(Update update, STATE_BOT stateBot) {
        AnswerBot answer = new AnswerBot();
        Message message = update.getMessage();
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, STATE_BOT.START.getTextMessage());
        MessageButtonView.setMessageButton(sendMessage, STATE_BOT.START.toString());
        answer.setAnswer(sendMessage);
        return answer;
    }
}
