package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.PCardDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface PCardDayDao extends JpaRepository<PCardDay, Long> {

    public default void addPCardDay(List<String[]> rows) {
        List<PCardDay> pCardDayList = new ArrayList<>();
        for(String[] row : rows) {
            PCardDay pCardDay = new PCardDay();
            pCardDay.setCard(row[0]);
            pCardDay.setText(row[1]);
            pCardDayList.add(pCardDay);
        }
        saveAll(pCardDayList);
    }

}
