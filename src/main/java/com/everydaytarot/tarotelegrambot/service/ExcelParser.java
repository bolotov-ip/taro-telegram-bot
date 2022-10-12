package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.business.PredictionManager;
import com.everydaytarot.tarotelegrambot.business.ServiceManager;
import com.everydaytarot.tarotelegrambot.exception.ParseXlsxException;
import com.everydaytarot.tarotelegrambot.model.Service;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelParser {

    @Autowired
    PredictionManager predictionManager;

    @Autowired
    ServiceManager serviceManager;

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    public void parseXlsxService(String path) {
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
                serviceList.add(service);
            }
            for(Service service : serviceList) {
                serviceManager.addService(service);
            }
        }
        catch (Exception e) {
            log.error("ExcelParce error: " + e.getMessage());
        }
    }

    public void parserPredictionXlsx(String path){
        try {
            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            String augury = "";
            String card = "";
            for (Row row : sheet) {
                String cellValue = row.getCell(0)!=null&&row.getCell(0).getCellType().equals(CellType.STRING)?row.getCell(0).getStringCellValue():"";

                if(cellValue!=null && !cellValue.isEmpty()) {
                    augury = row.getCell(0).getStringCellValue();
                }

                cellValue = row.getCell(1)!=null&&row.getCell(1).getCellType().equals(CellType.STRING)?row.getCell(1).getStringCellValue():"";

                if(cellValue!=null && !cellValue.isEmpty()) {
                    card = row.getCell(1).getStringCellValue();
                }

                String result = row.getCell(1)!=null&&row.getCell(2).getCellType().equals(CellType.STRING)?row.getCell(2).getStringCellValue():"";
                if(card == null || card.equals("") || augury == null || augury.equals("") ||result == null || result.equals(""))
                    throw new ParseXlsxException();
                predictionManager.saveAuguryResult(card, augury, result);
            }
        }
        catch (Exception e) {
            log.error("ExcelParce error: " + e.getMessage());
        }

    }

}
