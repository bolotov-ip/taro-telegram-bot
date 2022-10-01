package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.dao.StateDao;
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
public class EventHandler {

    @Autowired
    StateDao stateDao;

    @Autowired
    BotConfig botConfig;

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    public AnswerBot start(Update update) {
        AnswerBot answer = new AnswerBot();
        String chatId = "";
        if(update.hasCallbackQuery()) {
            Message message = update.getCallbackQuery().getMessage();
            chatId = String.valueOf(message.getChatId());
            long messageId = message.getMessageId();
            EditMessageText editMessage = new EditMessageText();
            editMessage.setChatId(chatId);
            editMessage.setMessageId((int)messageId);
            editMessage.setText(STATE_BOT.START.getTextMessage());
            MessageButtonView.setMessageButton(editMessage, STATE_BOT.START.toString());
            answer.setAnswer(editMessage);

        }
        else {
            Message message = update.getMessage();
            chatId = String.valueOf(message.getChatId());
            SendMessage sendMessage = new SendMessage(chatId, STATE_BOT.START.getTextMessage());
            MessageButtonView.setMessageButton(sendMessage, STATE_BOT.START.toString());
            answer.setAnswer(sendMessage);
        }

        stateDao.setState(STATE_BOT.START, Long.valueOf(chatId));
        return answer;
    }

    public AnswerBot pressMenu(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = String.valueOf(msg.getChatId());
        long messageId = msg.getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        String admin_menu = STATE_BOT.ADMIN_MENU.getTextMessage();
        editMessage.setText(STATE_BOT.ADMIN_MENU.getTextMessage());
        editMessage.setMessageId((int)messageId);
        MessageButtonView.setMessageButton(editMessage, STATE_BOT.ADMIN_MENU.toString());
        AnswerBot answer = new AnswerBot(editMessage);
        stateDao.setState(STATE_BOT.ADMIN_MENU, Long.valueOf(chatId));

        return answer;
    }

    public AnswerBot pressAddXLSX(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = String.valueOf(msg.getChatId());
        long messageId = msg.getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setText(STATE_BOT.INPUT_XLSX.getTextMessage());
        editMessage.setMessageId((int)messageId);
        MessageButtonView.setMessageButton(editMessage, STATE_BOT.INPUT_XLSX.toString());
        AnswerBot answer = new AnswerBot(editMessage);
        stateDao.setState(STATE_BOT.INPUT_XLSX, Long.valueOf(chatId));

        return answer;
    }

    public AnswerBot pressBack(Update update) {
        Message msg = update.getCallbackQuery().getMessage();
        STATE_BOT state = stateDao.getState(msg.getChatId());
        if(state.equals(STATE_BOT.ADMIN_MENU))
            return start(update);
        else if(state.equals(STATE_BOT.INPUT_XLSX))
            return pressMenu(update);
        return null;
    }

    public AnswerBot downloadExcel(Update update) {
        AnswerBot answer = new AnswerBot();
        Message message = update.getMessage();
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        answer.setAnswer(sendMessage);
        MessageButtonView.setMessageButton(sendMessage, STATE_BOT.START.toString());


        String fileName = update.getMessage().getDocument().getFileName();
        String fileId = update.getMessage().getDocument().getFileId();
        try {
            uploadFile(fileName, fileId, botConfig.getPathFile());
            sendMessage.setText(STATE_BOT.LOAD.getTextMessage());
            MessageButtonView.setMessageButton(sendMessage, STATE_BOT.LOAD.toString());
            stateDao.setState(STATE_BOT.LOAD, Long.valueOf(chatId));
        }
        catch (IOException e) {
            log.error("File download error: " + e.getMessage());
            sendMessage.setText(STATE_BOT.ERROR_LOAD.getTextMessage());
            MessageButtonView.setMessageButton(sendMessage, STATE_BOT.ERROR_LOAD.toString());
            stateDao.setState(STATE_BOT.ERROR_LOAD, Long.valueOf(chatId));
        }

        return answer;
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
}
