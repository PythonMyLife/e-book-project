package backend.DaoImpl;


import backend.Dao.OrderDao;
import backend.Entity.Order;
import backend.Entity.OrderItem;
import backend.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByUser(String username){
        return orderRepository.findAllByUser_Username(username);
    }

    @Override
    public List<Order> findAllByUserAndStatus(String username, Integer status){
        return orderRepository.findAllByUser_UsernameAndStatusEquals(username, status);
    }

    @Override
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    @Override
    public Order deleteOrder(Order order){
        order.setStatus(1);
        return orderRepository.save(order);
    }
}
