package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.dao.PredictionCartomancyDao;
import com.everydaytarot.tarotelegrambot.model.PredictionCartomancy;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Component
public class PredictionManager {

    @Autowired
    private PredictionCartomancyDao predictionCartomancyDao;

    private final Logger log = LoggerFactory.getLogger(PredictionManager.class);

    private void savePrediction(String card, String category, String text) {
        Optional<PredictionCartomancy> auguryResult =  predictionCartomancyDao.findPrediction(card, category);
        PredictionCartomancy newPredictionCartomancy;
        if(auguryResult.isEmpty())
            newPredictionCartomancy = new PredictionCartomancy();
        else
            newPredictionCartomancy = auguryResult.get();
        newPredictionCartomancy.setCard(card);
        newPredictionCartomancy.setCategory(category);
        newPredictionCartomancy.setText(text);
        predictionCartomancyDao.save(newPredictionCartomancy);
    }

    public List<String> getCardNames(){
        List<PredictionCartomancy> predictionCartomancies = predictionCartomancyDao.getCards();
        Set<String> cards = new HashSet<>();
        for(PredictionCartomancy predictionCartomancy : predictionCartomancies)
            cards.add(predictionCartomancy.getCategory());
        return new ArrayList<>(cards);
    }

    public List<String> getAllCategory(SERVICE_TYPE service_type) {
        List<PredictionCartomancy> predictionCartomancies = predictionCartomancyDao.getCategories();
        Set<String> categories = new HashSet<>();
        for(PredictionCartomancy predictionCartomancy : predictionCartomancies)
            categories.add(predictionCartomancy.getCategory());
        return new ArrayList<>(categories);
    }

    public void clearAuguryTables() {
        predictionCartomancyDao.deleteAll();
    }

    public String getAugury(String card, String category) {
        Optional<PredictionCartomancy> prediction =  predictionCartomancyDao.findPrediction(card, category);
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

}
