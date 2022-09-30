package com.everydaytarot.tarotelegrambot.telegram.constant;

public enum BUTTONS {
    BTN_USER_START_SERVICE("Получить предсказание"),
    BTN_ADMIN_ORDER_BUTTON("Выполнить заказ"),
    BTN_ADMIN_MENU("Меню"),
    BTN_BACK("Назад"),
    BTN_ADMIN_ADD_XLSX("Добавить excel");

    private String text;

    BUTTONS(String txt) {
        text = txt;
    }

    public String getText() {
        return text;
    }
}
