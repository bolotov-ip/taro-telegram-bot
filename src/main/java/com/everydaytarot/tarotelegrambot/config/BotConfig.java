package com.everydaytarot.tarotelegrambot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.owner}")
    private String ownerId;

    @Value("${bot.catalog}")
    private String pathFile;

    @Value("${bot.catalog.card}")
    private String catalogCard;

    @Value("${bot.catalog.augury}")
    private String catalogAugury;

    @Value("${bot.catalog.service}")
    private String catalogService;

    public String getCatalogService() {
        return catalogService;
    }

    public void setCatalogService(String catalogService) {
        this.catalogService = catalogService;
    }

    public String getCatalogCard() {
        return catalogCard;
    }

    public void setCatalogCard(String catalogCard) {
        this.catalogCard = catalogCard;
    }

    public String getCatalogAugury() {
        return catalogAugury;
    }

    public void setCatalogAugury(String catalogAugury) {
        this.catalogAugury = catalogAugury;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
}
