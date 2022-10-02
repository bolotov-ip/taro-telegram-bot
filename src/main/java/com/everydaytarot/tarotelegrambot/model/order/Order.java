package com.everydaytarot.tarotelegrambot.model.order;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name="orders")
public class Order {

    public static Order createOrder(Long idChat, Long idService) {
        Order order = new Order();
        order.setIdChat(idChat);
        order.setIdService(idService);
        order.setStatus(StatusOrder.ACTIVE.toString());
        order.setDateOrder(new Timestamp(System.currentTimeMillis()));
        return order;
    }

    @Id
    String idOrder;

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    Long idChat;

    Timestamp dateOrder;

    Long idService;

    String status;

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public Timestamp getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Timestamp dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Long getIdService() {
        return idService;
    }

    public void setIdService(Long idService) {
        this.idService = idService;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
