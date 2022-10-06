package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.service.Service;
import com.everydaytarot.tarotelegrambot.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Optional;

@Component
public class ServiceDao {

    @Autowired
    ServiceRepository serviceRepository;

    public void addService(Service service) {
        serviceRepository.save(service);
    }

    public Service getService(String name) {
        Optional<Service> service = serviceRepository.findById(name);
        return service.isPresent()?service.get():null;
    }

    public void removeService(Service service) {
        serviceRepository.delete(service);
    }

    public void removeAllService() {
        serviceRepository.deleteAll();
    }

    //На случай если понадобится увеличить быстродействие

    @Value("${spring.datasource.url}")
    private String urlDatabase;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

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
