package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageManager {

    @Autowired
    BotConfig botConfig;

    public List<String> getAllCardShirt() {
        return findDirNamesInCatalog();
    }

    public File getFileImageCard(String nameCard, String nameShirt) {
        if(nameCard ==null || nameShirt == null)
            return null;
        for (File myFile : new File(botConfig.getCatalogCard()).listFiles())
            if (myFile.isDirectory()) {
                if(nameShirt.equals(myFile.getName())) {
                  String catalog =  myFile.getAbsolutePath();
                    for (File card : new File(catalog).listFiles()) {
                        if(card.getName().startsWith(nameCard)) {
                            return card;
                        }
                    }
                }
            }
        return null;
    }

    private List<String> findDirNamesInCatalog() {
        List<String> listDirectories = new ArrayList<>();
        for (File myFile : new File(botConfig.getCatalogCard()).listFiles())
            if (myFile.isDirectory()) listDirectories.add(myFile.getName());
        return listDirectories;
    }

}
