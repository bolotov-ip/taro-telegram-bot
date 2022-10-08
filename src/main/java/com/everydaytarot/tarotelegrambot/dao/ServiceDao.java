package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.service.Service;
import com.everydaytarot.tarotelegrambot.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

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

    public List<Service> getAllService() {
        Iterable<Service> allService = serviceRepository.findAll();
        List<Service> result = new ArrayList<>();
        allService.forEach(result::add);

        return result;
    }

    public void removeService(Service service) {
        serviceRepository.delete(service);
    }

    public void removeAllService() {
        serviceRepository.deleteAll();
    }
}
