package com.everydaytarot.tarotelegrambot.telegram.constant;

import java.util.HashMap;
import java.util.Map;

public enum STATE_BOT {
    ADMIN_START,
    ADMIN_MENU,
    ADMIN_ADD_FILE_MENU,
    ADMIN_TYPE_SERVICE,
    INPUT_XLSX_PREDICTION,
    INPUT_XLSX_SERVICE,
    INPUT_CARD,
    ERROR_LOAD,
    LOAD,
    USER_START,
    ADMIN_FILE_NOT_FOUND,
    USER_SERVICE_LIST,
    USER_SERVICE_DETAILS,
    USER_SELECT_CATEGORY,
    USER_PAY,
    USER_FINISH;

    private static Map<String, String> textMessage = new HashMap<String, String>();
    static {
        textMessage.put("ADMIN_START", "Добро пожаловать");
        textMessage.put("ADMIN_MENU", "Меню");
        textMessage.put("INPUT_XLSX_AUGURY", "Загрузите файл Excel c расширением xlsx, содержащий\nструктуру и описание всех комбинаций результата.\nМожно предварительно скачать файл, исправить его и снова загрузить");
        textMessage.put("INPUT_XLSX_SERVICE", "Загрузите файл Excel c расширением xlsx, содержащий\nописание предоставляемых услуг.\nМожно предварительно скачать файл, исправить его и снова загрузить");
        textMessage.put("LOAD", "Файл загружен успешно");
        textMessage.put("ERROR_LOAD", "Файл не загружен. Попробуйте снова");
        textMessage.put("USER_START", "Привет!:new_moon_with_face:\nХотите узнать что вас ждет сегодня&#10067;&#127183;");

        textMessage.put("ADMIN_ADD_FILE_MENU", "Загрузить файлы для:");
        textMessage.put("ADMIN_FILE_NOT_FOUND", "Файл не найден");
        textMessage.put("USER_SERVICE_LIST", "Список услуг");
        textMessage.put("USER_SERVICE_DETAILS", "Подробности");
        textMessage.put("USER_SELECT_CATEGORY", "На что будем гадать?");
        textMessage.put("USER_PAY", "Оплатить услугу?");
        textMessage.put("USER_FINISH", "Заказ создан успешно.");
        textMessage.put("ADMIN_TYPE_SERVICE", "Выберите тип услуги");
    }

    public String getTextMessage() {
        return textMessage.get(this.toString());
    }
}
