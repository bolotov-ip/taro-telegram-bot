package com.everydaytarot.tarotelegrambot.telegram.constant;

import java.util.HashMap;
import java.util.Map;

public enum STATE_BOT {
    START, ADMIN_MENU, INPUT_XLSX, ERROR_LOAD, LOAD, DELETE_TABLES_AUGURY;

    private static Map<String, String> textMessage = new HashMap<String, String>();
    static {
        textMessage.put("START", "Добро пожаловать");
        textMessage.put("ADMIN_MENU", "Меню");
        textMessage.put("INPUT_XLSX", "Загрузите файл Excel c расширением xlsx, содержащий\n структуру и описание всех комбинаций результата");
        textMessage.put("LOAD", "Файл загружен успешно");
        textMessage.put("ERROR_LOAD", "Файл не загружен. Попробуйте снова");
        textMessage.put("DELETE_TABLES_AUGURY", "Таблицы предсказаний удалены успешно\n для того чтобы сервис продолжал работу загрузите новый файл с таблицей");
    }

    public String getTextMessage() {
        return textMessage.get(this.toString());
    }
}
