package com.everydaytarot.tarotelegrambot.telegram.constant;

import java.util.HashMap;
import java.util.Map;

public enum STATE_BOT {
    START, ADMIN_MENU, INPUT_XLSX, ERROR_LOAD, LOAD;

    private static Map<String, String> textMessage = new HashMap<String, String>();
    static {
        textMessage.put("START", "Добро пожаловать");
        textMessage.put("ADMIN_MENU", "Меню");
        textMessage.put("INPUT_XLSX", "Загрузите файл Excel c расширением xlsx, содержащий\n структуру и описание всех комбинаций результата");
        textMessage.put("LOAD", "Файл загружен успешно");
        textMessage.put("ERROR_LOAD", "Файл не загружен. Попробуйте снова");
    }

    public String getTextMessage() {
        return textMessage.get(this.toString());
    }
}
