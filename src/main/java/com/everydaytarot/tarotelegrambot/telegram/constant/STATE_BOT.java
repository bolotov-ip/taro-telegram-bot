package com.everydaytarot.tarotelegrambot.telegram.constant;

public enum STATE_BOT {
    START(null), BTN_ADMIN_MENU(STATE_BOT.START), INPUT_XLSX(STATE_BOT.BTN_ADMIN_MENU);
    STATE_BOT previousState;
    STATE_BOT(STATE_BOT prev) {
        previousState = prev;
    }

    public STATE_BOT getPreviousState() {
        if(previousState == null)
            return this;
        return previousState;
    }
}
