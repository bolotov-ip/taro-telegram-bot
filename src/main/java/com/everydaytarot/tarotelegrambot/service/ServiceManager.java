package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.dao.ServiceDao;
import com.everydaytarot.tarotelegrambot.model.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Component
public class ServiceManager {

    @Autowired
    ServiceDao serviceDao;

    private final Logger log = LoggerFactory.getLogger(ServiceManager.class);

    public Service getService(Long id) {
        Optional<Service> service = serviceDao.findById(id);
        return service.isPresent()?service.get():null;
    }

    public List<Service> getActiveServices(SERVICE_TYPE serviceType) {
        return serviceDao.getActiveServices(serviceType.toString());
    }

    public void parseFileExcel(String path, SERVICE_TYPE service_type) {
        try {
            int COUNT_COLUMN = 6;
            int START_ROW = 2;
            List<Service> serviceList = new ArrayList<>();

            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            int indexRow = 0;
            for (Row row : sheet) {
                indexRow++;
                if(indexRow < START_ROW)
                    continue;
                Service service = new Service();
                for(int i=0; i<COUNT_COLUMN; i++) {
                    String cellValue = "";
                    Cell cell = row.getCell(i);
                    if(cell.getCellType()!=null && cell.getCellType().equals(CellType.NUMERIC))
                        cellValue = String.valueOf(((Double)cell.getNumericCellValue()).intValue());
                    else if(cell.getCellType()!=null && cell.getCellType().equals(CellType.STRING))
                        cellValue = cell.getStringCellValue();
                    if(cellValue.equals(""))
                        continue;
                    if(i==0)
                        service.setName(cellValue);
                    else if(i==1)
                        service.setDescription(cellValue);
                    else if(i==2)
                        service.setCountDay(Integer.valueOf(cellValue));
                    else if(i==3)
                        service.setCountUse(Integer.valueOf(cellValue));
                    else if(i==4)
                        service.setMaxUse(Integer.valueOf(cellValue));
                    else if(i==5)
                        service.setPrice(Long.valueOf(cellValue));
                }
                service.setType(service_type.toString());
                serviceList.add(service);
            }
            deactivationActiveService(service_type);
            serviceDao.saveAllAndFlush(serviceList);
        }
        catch (Exception e) {
            log.error("ExcelParce error: " + e.getMessage());
        }
    }

    private void deactivationActiveService(SERVICE_TYPE serviceType) {
        List<Service> services = getActiveServices(serviceType);
        for(Service service : services) {
            service.setState(Service.State.NONACTIVE.toString());
        }
        serviceDao.saveAllAndFlush(services);
    }
}
