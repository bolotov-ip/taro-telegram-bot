package com.everydaytarot.tarotelegrambot.service.excel;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import com.everydaytarot.tarotelegrambot.dao.AuguryResultDao;
import com.everydaytarot.tarotelegrambot.exception.ParseXlsxException;
import com.everydaytarot.tarotelegrambot.telegram.TelegramBot;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelParser {

    @Autowired
    AuguryResultDao auguryResultDao;

    @Autowired
    BotConfig botConfig;

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    public void parserXlsx(String path){
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
                    auguryResultDao.saveTypeAugury(augury);
                }

                cellValue = row.getCell(1)!=null&&row.getCell(1).getCellType().equals(CellType.STRING)?row.getCell(1).getStringCellValue():"";

                if(cellValue!=null && !cellValue.isEmpty()) {
                    card = row.getCell(1).getStringCellValue();
                    auguryResultDao.saveCard(card);
                }

                String result = row.getCell(1)!=null&&row.getCell(2).getCellType().equals(CellType.STRING)?row.getCell(2).getStringCellValue():"";
                if(card == null || card.equals("") || augury == null || augury.equals("") ||result == null || result.equals(""))
                    throw new ParseXlsxException();
                auguryResultDao.saveAuguryResult(card, augury, result);
            }
        }
        catch (Exception e) {
            log.error("ExcelParce error: " + e.getMessage());
        }

    }

    public void deleteFileXlsx() {
        new File(botConfig.getCatalogXlsx()).mkdirs();
        Path path= Paths.get(botConfig.getCatalogXlsx());
        if(Files.exists(path))
            for (File myFile : new File(botConfig.getCatalogXlsx()).listFiles())
                if (myFile.isFile()) myFile.delete();
    }
}
