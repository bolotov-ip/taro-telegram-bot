package com.everydaytarot.tarotelegrambot.model;

import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "users")
public class User {
    static enum Role { USER, ADMIN; }
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

        return user;
    }

    public boolean isAdmin() {
        if(role!=null)
            return role.contains(Role.ADMIN.toString());
        return false;
    }

    @Id
    @Column(name = "id_chat")
    Long idChat;

    @Column(name = "user_name")
    String userName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "date_registration")
    Timestamp dateRegistration;

    @Column(name = "role")
    String role;


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


    @Override
    public String toString() {
        return "User{" +
                "idChat=" + idChat +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateRegistration=" + dateRegistration +
                ", role='" + role +
                '}';
    }
}
