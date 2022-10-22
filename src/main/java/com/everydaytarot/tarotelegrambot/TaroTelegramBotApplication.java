package com.everydaytarot.tarotelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class TaroTelegramBotApplication {


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(TaroTelegramBotApplication.class, args);
		System.out.println("running");

	}
}
