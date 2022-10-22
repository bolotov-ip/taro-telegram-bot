package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.model.Subs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SubsDao extends JpaRepository<Subs, Long> {

    @Query("SELECT s FROM subs s where s.state='ACTIVE' and s.type=:type")
    List<Subs> getActiveSubs(@Param("type") String type);

    public default Subs getSubs(Long id) {
        Optional<Subs> service = findById(id);
        return service.isPresent()?service.get():null;
    }

    public default List<Subs> getActiveSubs(SERVICE_TYPE serviceType) {
        SubsDao subsDao;
        return getActiveSubs(serviceType.toString());
    }

    public default void deactivationActiveSubs(SERVICE_TYPE serviceType) {
        List<Subs> subcriptions = getActiveSubs(serviceType);
        for(Subs subs : subcriptions) {
            subs.setState(Subs.STATE_SUBS.NONACTIVE.toString());
        }
        saveAllAndFlush(subcriptions);
    }

    public default void addSubs(List<String[]> rows) {
        List<Subs> listSubs = new ArrayList<>();
        for(String[] row : rows) {
            Subs subs = new Subs();
            subs.setName(row[0]);
            subs.setDescription(row[1]);
            subs.setCountDay(Integer.valueOf(row[2]));
            subs.setCountUse(Integer.valueOf(row[3]));
            subs.setMaxUse(Integer.valueOf(row[4]));
            subs.setPrice(Long.valueOf(row[5]));
            subs.setType(row[6]);
            subs.setState(Subs.STATE_SUBS.ACTIVE.toString());
            listSubs.add(subs);
        }
        saveAll(listSubs);
    }
}
