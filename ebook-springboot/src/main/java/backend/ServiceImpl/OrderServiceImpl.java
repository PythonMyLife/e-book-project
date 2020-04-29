package backend.ServiceImpl;

import backend.Dao.BookDao;
import backend.Dao.CartDao;
import backend.Dao.OrderDao;
import backend.Dao.OrderItemDao;
import backend.Entity.Cart;
import backend.Entity.Order;
import backend.Entity.OrderItem;
import backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    CartDao cartDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    OrderItemDao orderItemDao;
    @Autowired
    KafkaTemplate kafkaTemplate;

    @Override
    public List<Order> findAllOrder(){
        List<Order> orderList = orderDao.findAll();
        for(Order order : orderList){
            order.getUser().setPassword("");
            order.getUser().setEmail("");
        }
        return orderList;
    }

    @Override
    public List<Order> findAllOrderByUsernameAndStatus(String username){
        List<Order> orderList = orderDao.findAllByUserAndStatus(username, 0);
        for(Order order : orderList){
            order.getUser().setPassword("");
            order.getUser().setEmail("");
        }
        return orderList;
    }

    @Override
    public Boolean submitOrder(String username){
        try{
            kafkaTemplate.send("ebook-order", username);
            return true;
        }catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
