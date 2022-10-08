package com.everydaytarot.tarotelegrambot.telegram.constant;

public enum BUTTONS {

    BTN_BACK("Назад"),
    BTN_BACK_TO_START("В главное меню"),
    BTN_CANCEL("Отмена"),
    BTN_ADMIN_ORDER_BUTTON("Выполнить заказ"),
    BTN_ADMIN_MENU("Меню"),
    BTN_ADMIN_ADD_FILE("Добавить файл"),
    BTN_ADMIN_ADD_XLSX_SERVICE("Добавить excel со списком услуг."),
    BTN_ADMIN_ADD_XLSX_AUGURY("Добавить excel со списком предсказаний"),
    BTN_ADMIN_AGAIN_LOAD("Загрузить снова"),
    BTN_ADMIN_DOWNOLAD_FILE("Скачать файл с сервера"),
    BTN_USER_MENU("Меню"),
    BTN_USER_MENU_SERVICE("Получить предсказание"),
    BTN_USER_START_SERVICE("Начать");

    private String text;

    BUTTONS(String txt) {
        text = txt;
    }

    public String getText() {
        return text;
    }
}
