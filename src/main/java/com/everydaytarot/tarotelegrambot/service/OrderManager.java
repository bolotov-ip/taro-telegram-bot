package com.everydaytarot.tarotelegrambot.service;

import com.everydaytarot.tarotelegrambot.dao.OrderDao;
import com.everydaytarot.tarotelegrambot.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderManager {

    @Autowired
    OrderDao orderDao;

    @Autowired
    private SessionFactory sessionFactory;



    private final Logger log = LoggerFactory.getLogger(OrderManager.class);

    public void addOrder(Order order) {
        orderDao.save(order);
    }

    public Order getPayActiveOrder(){
        String sql = "SELECT * FROM orders AS o WHERE o.price>0 and o.end_use < CURRENT_TIMESTAMP and o.count_use < o.max_use and o.last_use < CURRENT_DATE LIMIT 1";
        final Order[] order = {null};
        Session session = sessionFactory.openSession();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try(PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery(); ){
                    while (rs.next()) {
                        order[0] =  Order.createOrder(rs);
                    }
                }
                catch (SQLException ex) {
                    log.error(ex.getMessage());
                }
            }
        });
        return order[0];
    }

    public void save(Order order) {
        orderDao.save(order);
    }
}
