package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.dao.PredictionDao;
import com.everydaytarot.tarotelegrambot.model.Prediction;
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
    private PredictionDao predictionDao;

    private final Logger log = LoggerFactory.getLogger(PredictionManager.class);

    private void savePrediction(String card, String category, String text) {
        Optional<Prediction> auguryResult =  predictionDao.findPrediction(card, category);
        Prediction newPrediction;
        if(auguryResult.isEmpty())
            newPrediction = new Prediction();
        else
            newPrediction = auguryResult.get();
        newPrediction.setCard(card);
        newPrediction.setCategory(category);
        newPrediction.setText(text);
        predictionDao.save(newPrediction);
    }

    public List<String> getCardNames(){
        List<Prediction> predictions = predictionDao.getCards();
        Set<String> cards = new HashSet<>();
        for(Prediction prediction : predictions)
            cards.add(prediction.getCategory());
        return new ArrayList<>(cards);
    }

    public List<String> getAllCategory() {
        List<Prediction> predictions = predictionDao.getCategories();
        Set<String> categories = new HashSet<>();
        for(Prediction prediction : predictions)
            categories.add(prediction.getCategory());
        return new ArrayList<>(categories);
    }

    public void clearAuguryTables() {
        predictionDao.deleteAll();
    }

    public String getAugury(String card, String category) {
        Optional<Prediction> prediction =  predictionDao.findPrediction(card, category);
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
                if(card == null || card.equals("") || category == null || category.equals("") ||text == null || text.equals(""))
                    continue;
                this.savePrediction(card, category, text);
            }
        }
        catch (Exception e) {
            log.error("ExcelParce error: " + e.getMessage());
        }
    }

}
