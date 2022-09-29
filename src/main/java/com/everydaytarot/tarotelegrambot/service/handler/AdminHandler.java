package com.everydaytarot.tarotelegrambot.service.handler;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.repository.UserRepository;
import com.everydaytarot.tarotelegrambot.service.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdminHandler implements Handler{

    private final static Map<String, String> commands = new HashMap<>();

    static {
        commands.put("/start", "Главное меню");
    }

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private TelegramBot bot;

    private Update update;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BotConfig config;

    public void setBot(TelegramBot bot) {
        this.bot = bot;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    @Override
    public void run() {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textMessage = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            if(textMessage.equals("/start")) {
                SendMessage sendMessage = new SendMessage(chatId, commands.get(textMessage));
                ViewButton.setStartButton(sendMessage);
                Sender.sendMessage(bot, sendMessage);
            }
            else {
                boolean isNameCard = false;
                if(isNameCard)
                    sendResultUser(textMessage);
                else {
                    SendMessage send = new SendMessage(chatId, "Команда не поддерживается");
                    Sender.sendMessage(bot, send);
                }

            }
        } else if(update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
        }
    }

    public void sendResultUser(String card) {
        //Определить бота для
    }
}
