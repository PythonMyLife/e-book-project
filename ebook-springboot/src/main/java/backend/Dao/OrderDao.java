package backend.Dao;


import backend.Entity.Order;
import backend.Entity.OrderItem;

import java.util.List;

public interface OrderDao {
    List<Order> findAll();
    List<Order> findAllByUser(String username);
    List<Order> findAllByUserAndStatus(String username, Integer status);

    Order saveOrder(Order order);

    Order deleteOrder(Order order);
}
