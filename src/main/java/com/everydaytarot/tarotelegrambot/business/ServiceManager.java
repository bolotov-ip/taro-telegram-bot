package com.everydaytarot.tarotelegrambot.business;

import com.everydaytarot.tarotelegrambot.dao.ServiceDao;
import com.everydaytarot.tarotelegrambot.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ServiceManager {

    @Autowired
    ServiceDao serviceDao;

    public void addService(Service service) {
        serviceDao.save(service);
    }

    public Service getService(Long id) {
        Optional<Service> service = serviceDao.findById(id);
        return service.isPresent()?service.get():null;
    }

    public List<Service> getAllService() {
        return serviceDao.findAll();
    }

    public void removeService(Service service) {
        serviceDao.delete(service);
    }

    public void removeAllService() {
        serviceDao.deleteAll();
    }
}
