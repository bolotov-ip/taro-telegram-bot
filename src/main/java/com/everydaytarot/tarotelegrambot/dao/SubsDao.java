package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.config.SERVICE_TYPE;
import com.everydaytarot.tarotelegrambot.model.Subs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubsDao extends JpaRepository<Subs, Long> {

    @Query("SELECT s FROM service s where s.state='ACTIVE' and s.type=:type")
    List<Subs> getActiveServices(@Param("type") String type);

    public default Subs getService(Long id) {
        Optional<Subs> service = findById(id);
        return service.isPresent()?service.get():null;
    }

    public default List<Subs> getActiveServices(SERVICE_TYPE serviceType) {
        SubsDao subsDao;
        return getActiveServices(serviceType.toString());
    }

    public default void deactivationActiveService(SERVICE_TYPE serviceType) {
        List<Subs> subcriptions = getActiveServices(serviceType);
        for(Subs subs : subcriptions) {
            subs.setState(Subs.State.NONACTIVE.toString());
        }
        saveAllAndFlush(subcriptions);
    }
}
