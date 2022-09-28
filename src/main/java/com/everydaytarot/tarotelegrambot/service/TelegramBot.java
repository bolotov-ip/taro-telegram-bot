package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.model.User;
import com.everydaytarot.tarotelegrambot.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    BotConfig config;

    @Autowired
    UserRepository userRepository;

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        //Document inputDocument = update.getMessage().getDocument();
       // File outputFile = new File(config.getPathFile()+ "file-test.txt");
        GetFile getFileRequest = new GetFile();
        List<PhotoSize> photos = update.getMessage().getPhoto();
        for(PhotoSize photo : photos) {
            String id = photo.getFileId();
            getFileRequest.setFileId(id);
            try {
                org.telegram.telegrambots.meta.api.objects.File telegramFile =  execute(getFileRequest);
                File testFile = downloadFile(telegramFile);
                String path = testFile.getPath();
                System.out.println(path);
                testFile.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            SendPhoto document = null;
            message.setChatId(String.valueOf(chatId));
            switch (messageText) {
                case "/start":
                    message.setText("Test bot");
                    InputFile file = new InputFile(new File("C:\\Users\\bolotov-ip\\Desktop\\images.jpg"));
                    document = new SendPhoto(String.valueOf(chatId), file);

                    if(userRepository.findById(chatId).isEmpty()) {
                        User user = new User();
                        user.setChatId(chatId);
                        user.setFirstName(update.getMessage().getChat().getFirstName());
                        userRepository.save(user);
                    }
                    try {
                        execute(message);
                        execute(document);
                    }
                    catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    message.setText("Command undefined");
                    try {
                        execute(message);
                        execute(document);
                    }
                    catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }

        }
    }
}
