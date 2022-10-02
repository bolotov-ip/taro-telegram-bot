package com.everydaytarot.tarotelegrambot.telegram.constant;

import java.util.HashMap;
import java.util.Map;

public enum STATE_BOT {
    ADMIN_START, ADMIN_MENU, ADMIN_ADD_FILE_MENU, INPUT_XLSX_AUGURY, INPUT_XLSX_SERVICE, INPUT_CARD, ERROR_LOAD, LOAD, USER_START, ADMIN_LIST_SERVICE, ADMIN_SHOW_SERVICES, ADMIN_DOWNLOAD_SERVICE_XLSX, ADMIN_DOWNLOAD_AUGURE_XLSX,
    ADMIN_FILE_NOT_FOUND;

    private static Map<String, String> textMessage = new HashMap<String, String>();
    static {
        textMessage.put("ADMIN_START", "Добро пожаловать");
        textMessage.put("ADMIN_MENU", "Меню");
        textMessage.put("INPUT_XLSX_AUGURY", "Загрузите файл Excel c расширением xlsx, содержащий\nструктуру и описание всех комбинаций результата.\nМожно предварительно скачать файл, исправить его и снова загрузить");
        textMessage.put("INPUT_XLSX_SERVICE", "Загрузите файл Excel c расширением xlsx, содержащий\nописание предоставляемых услуг.\nМожно предварительно скачать файл, исправить его и снова загрузить");
        textMessage.put("LOAD", "Файл загружен успешно");
        textMessage.put("ERROR_LOAD", "Файл не загружен. Попробуйте снова");
        textMessage.put("USER_START", "Привет! Хотите узнать что вас ждет сегодня?");
        textMessage.put("ADMIN_LIST_SERVICE", "Услуги");
        textMessage.put("INPUT_CARD", "Загрузите фото карт");
        textMessage.put("ADMIN_ADD_FILE_MENU", "Загрузить файлы для:");
        textMessage.put("ADMIN_FILE_NOT_FOUND", "Файл не найден");
    }

    public String getTextMessage() {
        return textMessage.get(this.toString());
    }
}
