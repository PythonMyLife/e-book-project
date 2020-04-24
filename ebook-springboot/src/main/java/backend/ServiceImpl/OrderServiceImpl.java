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
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Order> findAllOrder(){
        List<Order> orderList = orderDao.findAll();
        for(Order order : orderList){
            order.getUser().setPassword("");
            order.getUser().setEmail("");
        }
        return orderList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Order> findAllOrderByUsernameAndStatus(String username){
        List<Order> orderList = orderDao.findAllByUserAndStatus(username, 0);
        for(Order order : orderList){
            order.getUser().setPassword("");
            order.getUser().setEmail("");
        }
        return orderList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Boolean submitOrder(String username){
        try{
            kafkaTemplate.send("ebook-order", username);
            return true;
        }catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
//        List<Cart> cartList = cartDao.getUserCart(username);
//        if(cartList.isEmpty()) return false;
//        for(Cart cart: cartList){
//            if(cart.getNum() > cart.getBook().getNum())
//                return false;
//        }
//
//        Order order = new Order();
//        order.setUser(cartList.get(0).getUser());
//        order.setStatus(0);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateTime = df.format(new Date());
//        order.setTime(dateTime);
//        orderDao.saveOrder(order);
//        for(Cart cart: cartList){
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder_id(order.getOrder_id());
//            orderItem.setBook(cart.getBook());
//            orderItem.setNumber(cart.getNum());
//            orderItemDao.saveItem(orderItem);
//            cartDao.deleteCart(cart.getBook(),cart.getUser());
//            bookDao.setBookNum(cart.getBook(),cart.getBook().getNum() - cart.getNum());
//        }
//
//        return true;
    }
}
