package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.dao.PCardIndividualDao;
import com.everydaytarot.tarotelegrambot.dao.SubsDao;
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
    private PCardIndividualDao pCardIndividualDao;

    @Autowired
    private SubsDao subsDao;

    private final Logger log = LoggerFactory.getLogger(SettingsManager.class);

    private void savePrediction(String card, String category, String text) {
        Optional<PCardIndividual> auguryResult =  pCardIndividualDao.findPrediction(card, category);
        PCardIndividual newPCardIndividual;
        if(auguryResult.isEmpty())
            newPCardIndividual = new PCardIndividual();
        else
            newPCardIndividual = auguryResult.get();
        newPCardIndividual.setCard(card);
        newPCardIndividual.setCategory(category);
        newPCardIndividual.setText(text);
        pCardIndividualDao.save(newPCardIndividual);
    }

    public List<String> getCardNames(){
        List<PCardIndividual> predictionCartomancies = pCardIndividualDao.getCards();
        Set<String> cards = new HashSet<>();
        for(PCardIndividual pCardIndividual : predictionCartomancies)
            cards.add(pCardIndividual.getCategory());
        return new ArrayList<>(cards);
    }

    public List<String> getAllCategory(SERVICE_TYPE service_type) {
        List<PCardIndividual> predictionCartomancies = pCardIndividualDao.getCategories();
        Set<String> categories = new HashSet<>();
        for(PCardIndividual pCardIndividual : predictionCartomancies)
            categories.add(pCardIndividual.getCategory());
        return new ArrayList<>(categories);
    }

    public void clearAuguryTables() {
        pCardIndividualDao.deleteAll();
    }

    public String getAugury(String card, String category) {
        Optional<PCardIndividual> prediction =  pCardIndividualDao.findPrediction(card, category);
        if(prediction.isPresent())
            return prediction.get().getText();
        else
            return "";
    }

    public void parseFileExcel(String path) {
        try {
            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            String category = "";
            String card = "";
            for (Row row : sheet) {
                String cellValue = row.getCell(0)!=null&&row.getCell(0).getCellType().equals(CellType.STRING)?row.getCell(0).getStringCellValue():"";

                if(cellValue!=null && !cellValue.isEmpty()) {
                    category = row.getCell(0).getStringCellValue();
                }

                cellValue = row.getCell(1)!=null&&row.getCell(1).getCellType().equals(CellType.STRING)?row.getCell(1).getStringCellValue():"";

                if(cellValue!=null && !cellValue.isEmpty()) {
                    card = row.getCell(1).getStringCellValue();
                }

                String text = row.getCell(1)!=null&&row.getCell(2).getCellType().equals(CellType.STRING)?row.getCell(2).getStringCellValue():"";
                if(text == null || text.equals(""))
                    continue;
                this.savePrediction(card, category, text);
            }
        }
        catch (Exception e) {
            log.error("ExcelParce error: " + e.getMessage());
        }
    }

    public void parseFileExcel(String path, SERVICE_TYPE service_type) {
        try {
            int COUNT_COLUMN = 6;
            int START_ROW = 2;
            List<Subs> subsList = new ArrayList<>();

            FileInputStream file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            int indexRow = 0;
            for (Row row : sheet) {
                indexRow++;
                if(indexRow < START_ROW)
                    continue;
                Subs subs = new Subs();
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
                        subs.setName(cellValue);
                    else if(i==1)
                        subs.setDescription(cellValue);
                    else if(i==2)
                        subs.setCountDay(Integer.valueOf(cellValue));
                    else if(i==3)
                        subs.setCountUse(Integer.valueOf(cellValue));
                    else if(i==4)
                        subs.setMaxUse(Integer.valueOf(cellValue));
                    else if(i==5)
                        subs.setPrice(Long.valueOf(cellValue));
                }
                subs.setServiceType(service_type.toString());
                subsList.add(subs);
            }
            subsDao.deactivationActiveService(service_type);
            subsDao.saveAllAndFlush(subsList);
        }
        catch (Exception e) {
            log.error("ExcelParce error: " + e.getMessage());
        }
    }
}
