package com.everydaytarot.tarotelegrambot.telegram.constant;

import java.util.HashMap;
import java.util.Map;

public enum STATE_BOT {
    START(null), BTN_ADMIN_MENU(STATE_BOT.START), INPUT_XLSX(STATE_BOT.BTN_ADMIN_MENU);

    private STATE_BOT previousState;
    private static Map<String, String> textMessage = new HashMap<String, String>();
    static {
        textMessage.put("START", "Добро пожаловать");
        textMessage.put("BTN_ADMIN_MENU", "Меню");
        textMessage.put("INPUT_XLSX", "Загрузите файл Excel c расширением xlsx, содержащий\n структуру и описание всех комбинаций результата");
    }

    STATE_BOT(STATE_BOT prev) {
        previousState = prev;
    }

    public STATE_BOT getPreviousState() {
        if(previousState == null)
            return this;
        return previousState;
    }

    public String getTextMessage() {
        return textMessage.get(this.toString());
    }
}
