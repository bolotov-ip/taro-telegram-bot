package com.everydaytarot.tarotelegrambot.service.excel;

import com.everydaytarot.tarotelegrambot.dao.AuguryResultDao;
import com.everydaytarot.tarotelegrambot.exception.ParseXlsxException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelParser {

    @Autowired
    AuguryResultDao auguryResultDao;

    public void parserXlsx(String path) throws IOException, ParseXlsxException {
        FileInputStream file = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int indexRow = 0;

        for (Row row : sheet) {
            String card = row.getCell(0).getStringCellValue();
            String augury = row.getCell(1).getStringCellValue();
            String result = row.getCell(2).getStringCellValue();
            if(card == null || card.equals("") || augury == null || augury.equals("") ||result == null || result.equals(""))
                throw new ParseXlsxException();
            auguryResultDao.saveTypeAugury(augury);
            auguryResultDao.saveCard(card);
            auguryResultDao.saveAuguryResult(card, augury, result);
            indexRow++;
        }
    }

    public void deleteFileXlsx() {

    }
}
