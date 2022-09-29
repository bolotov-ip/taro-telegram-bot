package com.everydaytarot.tarotelegrambot.model;

import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "users")
public class User {

    public static User createUserAdmin(Message msg) {
        User user = createUser(msg);
        user.setRole(Role.USER.toString() + ";" + Role.ADMIN.toString());
        return user;
    }

    public static User createUser(Message msg) {
        User user = new User();
        user.setIdChat(msg.getChatId());
        user.setUserName(msg.getChat().getUserName());
        user.setFirstName(msg.getChat().getFirstName());
        user.setLastName(msg.getChat().getLastName());
        user.setDateRegistration(new Timestamp(System.currentTimeMillis()));
        user.setRole(Role.USER.toString());
        user.setIdSubsciption(0L);

        return user;
    }

    @Id
    Long idChat;

    String userName;

    String firstName;

    String lastName;

    Timestamp dateRegistration;

    String role;

    Long idSubsciption;

    String latestComand;

    public String getLatestComand() {
        return latestComand;
    }

    public void setLatestComand(String latestComand) {
        this.latestComand = latestComand;
    }

    public Timestamp getDateLatestComand() {
        return dateLatestComand;
    }

    public void setDateLatestComand(Timestamp dateLatestComand) {
        this.dateLatestComand = dateLatestComand;
    }

    Timestamp dateLatestComand;

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Timestamp dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getIdSubsciption() {
        return idSubsciption;
    }

    public void setIdSubsciption(Long idSubsciption) {
        this.idSubsciption = idSubsciption;
    }

    @Override
    public String toString() {
        return "User{" +
                "idChat=" + idChat +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateRegistration=" + dateRegistration +
                ", role='" + role + '\'' +
                ", idSubsciption=" + idSubsciption +
                '}';
    }
}
