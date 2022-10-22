package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.dao.PCardDayDao;
import com.everydaytarot.tarotelegrambot.dao.PCardIndividualDao;
import com.everydaytarot.tarotelegrambot.dao.SubsDao;
import com.everydaytarot.tarotelegrambot.model.PCardDay;
import com.everydaytarot.tarotelegrambot.model.PCardIndividual;
import com.everydaytarot.tarotelegrambot.model.Subs;
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
public class SettingsManager {

    @Autowired
    private PCardDayDao pCardDayDao;

    @Autowired
    private PCardIndividualDao pCardIndividualDao;

    @Autowired
    private SubsDao subsDao;

    private final Logger log = LoggerFactory.getLogger(SettingsManager.class);

    public boolean updatePrediction(SERVICE_TYPE serviceType, String pathFile) {
        List<String[]> listData = parseExcel(serviceType, pathFile);
        switch (serviceType) {
            case CARD_OF_THE_DAY:
                pCardDayDao.deleteAll();
                pCardDayDao.addPCardDay(listData);
                break;
            case CARD_INDIVIDUAL:
                pCardIndividualDao.deleteAll();
                pCardIndividualDao.addPCardIndividual(listData);
                break;
        }
        return true;
    }

    public boolean updateSubs(SERVICE_TYPE serviceType, String pathFile) {
        List<String[]> listData = parseExcel(serviceType, pathFile);
        subsDao.deactivationActiveSubs(serviceType);
        subsDao.addSubs(listData);
        return true;
    }


    private List<String[]> parseExcel(SERVICE_TYPE serviceType,String pathFile) {
        try {
            List<String[]> result = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(new FileInputStream(new File(pathFile)));
            Sheet sheet = workbook.getSheetAt(0);
            int rowSize = getCountColumn(sheet);
            int rowNum = 0;
            for (Row row : sheet) {
                String[] rowData = new String[rowSize];
                for(int i=0; i<rowData.length; i++) {
                    if(i==rowData.length-1)
                        rowData[i] = serviceType.toString();
                    String cellValue = "";
                    Cell cell = row.getCell(i);
                    if(cell.getCellType()!=null && cell.getCellType().equals(CellType.NUMERIC))
                        cellValue = String.valueOf(((Double)cell.getNumericCellValue()).intValue());
                    else if(cell.getCellType()!=null && cell.getCellType().equals(CellType.STRING))
                        cellValue = cell.getStringCellValue();
                    rowData[i] = cellValue;
                    if(result.size()>1 && (rowData[i]==null || rowData[i].isEmpty()))
                        rowData[i] = result.get(rowNum-1)[i];
                }
                result.add(rowData);
                rowNum++;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private int getCountColumn(Sheet sheet) {
        return sheet.getRow(0).getLastCellNum() + 1;
    }

}
