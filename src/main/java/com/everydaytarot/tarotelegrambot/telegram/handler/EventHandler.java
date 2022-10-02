package com.everydaytarot.tarotelegrambot.telegram.handler;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.dao.StateDao;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import com.everydaytarot.tarotelegrambot.telegram.constant.BUTTONS;
import com.everydaytarot.tarotelegrambot.telegram.constant.STATE_BOT;
import com.everydaytarot.tarotelegrambot.telegram.domain.AnswerBot;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventHandler {

    @Autowired
    protected StateDao stateDao;

    @Autowired
    protected BotConfig botConfig;

    protected final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    public AnswerBot commandNotSupport(Update update) {
        SendMessage send = new SendMessage(String.valueOf(update.getMessage().getChatId()), "Команда не поддерживается");
        AnswerBot answer = new AnswerBot(send);
        return answer;
    }

    protected AnswerBot setCommonCallbackAnswer(Update update, STATE_BOT stateBot, List<BUTTONS> btnList, int countColumn) {
        Message msg = update.getCallbackQuery().getMessage();
        String chatId = String.valueOf(msg.getChatId());
        long messageId = msg.getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setText(stateBot.getTextMessage());
        editMessage.setMessageId((int)messageId);
        setButton(btnList, editMessage, countColumn);
        AnswerBot answer = new AnswerBot(editMessage);
        stateDao.setState(stateBot, Long.valueOf(chatId));

        return answer;
    }

    protected AnswerBot setCommonAnswer(Update update, STATE_BOT stateBot, List<BUTTONS> btnList, int countColumn) {
        AnswerBot answer = new AnswerBot();
        Message message = update.getMessage();
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage(chatId, STATE_BOT.ADMIN_START.getTextMessage());
        setButton(btnList, sendMessage, countColumn);
        answer.setAnswer(sendMessage);
        return answer;
    }

    protected void replyMarkup(InlineKeyboardMarkup markupInLine, BotApiMethod<?> msg) {
        if(msg instanceof SendMessage)
            ((SendMessage)msg).setReplyMarkup(markupInLine);
        else if(msg instanceof EditMessageText) {
            ((EditMessageText)msg).setReplyMarkup(markupInLine);
        }
    }

    protected void setButton(List<BUTTONS> btnList, BotApiMethod<?> msg, int countColumn) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        int countAddRowBtn = 0;
        for(BUTTONS btn : btnList) {
            if(countAddRowBtn<countColumn){
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(btn.getText());
                button.setCallbackData(btn.toString());
                rowInLine.add(button);
                countAddRowBtn++;
            }
            else {
                rowsInLine.add(rowInLine);
                rowInLine = new ArrayList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(btn.getText());
                button.setCallbackData(btn.toString());
                rowInLine.add(button);
                countAddRowBtn=1;
            }
        }
        if(!rowsInLine.contains(rowInLine))
            rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        replyMarkup(markupInLine, msg);
    }

    protected String uploadFile(String fileName, String fileId, String pathFile) throws IOException {
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
        return pathFile + fileName;
    }
}
