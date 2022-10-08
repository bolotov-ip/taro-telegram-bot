package com.everydaytarot.tarotelegrambot.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class ConnectionDB {

    @Value("${spring.datasource.url}")
    private String urlDatabase;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private static Connection connection;

    public Connection getConnection() throws SQLException {
        if(connection==null) {
            connection = DriverManager.getConnection(urlDatabase, username, password);
        }
        return connection;
    }

    public void close() throws SQLException {
        if(connection !=null) {
            connection.close();
        }
    }

    public void testConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(urlDatabase, username, password);
        String updatePositionSql = "select * from users";
        try (PreparedStatement pstmt = connection.prepareStatement(updatePositionSql); ResultSet rs = pstmt.executeQuery();) {
            while(rs.next()) {
                String field = rs.getString(1);
                System.out.println(field);
            }
        }
        connection.close();
    }
}
