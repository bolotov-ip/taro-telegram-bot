package com.everydaytarot.tarotelegrambot.model.service;

import javax.persistence.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity(name = "orders")
public class Order {

    public static Order createOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        OrderId orderId = new OrderId(rs.getLong("chat_id"), rs.getString("name_service"));
        order.setOrderId(orderId);
        order.setCountUse(rs.getInt("count_use"));
        order.setMaxUse(rs.getInt("max_use"));
        order.setAllUse(rs.getInt("all_use"));
        order.setMaxAllUse(rs.getInt("max_all_use"));
        order.setLastUse(rs.getDate("last_use"));
        order.setEndUse(rs.getTimestamp("end_use"));
        order.setPrice(rs.getLong("price"));
        order.setCategory(rs.getString("category"));
        return order;
    }

    @EmbeddedId
    OrderId orderId;

    @Column(name = "category")
    String category;

    @Column(name ="count_use")
    Integer countUse;

    @Column(name = "max_use")
    Integer maxUse;

    @Column(name = "all_use")
    Integer allUse;

    @Column(name = "max_all_use")
    Integer maxAllUse;

    @Column(name = "last_use")
    Date lastUse;

    @Column(name = "end_use")
    Timestamp endUse;

    @Column(name = "price")
    Long price;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public Integer getCountUse() {
        return countUse;
    }

    public void setCountUse(Integer countUse) {
        this.countUse = countUse;
    }

    public Integer getMaxUse() {
        return maxUse;
    }

    public void setMaxUse(Integer maxUse) {
        this.maxUse = maxUse;
    }

    public Integer getAllUse() {
        return allUse;
    }

    public void setAllUse(Integer allUse) {
        this.allUse = allUse;
    }

    public Integer getMaxAllUse() {
        return maxAllUse;
    }

    public void setMaxAllUse(Integer maxAllUse) {
        this.maxAllUse = maxAllUse;
    }

    public Date getLastUse() {
        return lastUse;
    }

    public void setLastUse(Date lastUse) {
        this.lastUse = lastUse;
    }

    public Timestamp getEndUse() {
        return endUse;
    }

    public void setEndUse(Timestamp endUse) {
        this.endUse = endUse;
    }
}
