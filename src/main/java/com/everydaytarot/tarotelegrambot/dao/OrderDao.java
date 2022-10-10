package com.everydaytarot.tarotelegrambot.dao;

import com.everydaytarot.tarotelegrambot.model.service.Order;
import com.everydaytarot.tarotelegrambot.model.service.OrderId;
import com.everydaytarot.tarotelegrambot.repository.OrderRepository;
import com.everydaytarot.tarotelegrambot.telegram.handler.EventAdminHandler;
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
import java.util.Optional;

@Component
public class OrderDao {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private SessionFactory sessionFactory;



    private final Logger log = LoggerFactory.getLogger(OrderDao.class);

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOrder(OrderId orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isPresent())
            return order.get();
        return null;
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
        orderRepository.save(order);
    }
}
