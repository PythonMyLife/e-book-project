package backend.ServiceImpl;

import backend.Dao.BookDao;
import backend.Dao.CartDao;
import backend.Dao.OrderDao;
import backend.Dao.OrderItemDao;
import backend.Entity.Cart;
import backend.Entity.Order;
import backend.Entity.OrderItem;
import backend.Service.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class KafkaServiceImpl implements KafkaService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BookDao bookDao;


    @KafkaListener(groupId = "spring", topics = {"ebook-order"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void dealOrder(ConsumerRecord record) {
        String username = (String) record.value();
        System.out.println(username);
        List<Cart> cartList = cartDao.getUserCart(username);
        if(cartList.isEmpty()) return ;
        for(Cart cart: cartList){
            if(cart.getNum() > cart.getBook().getNum())
                return ;
        }

        Order order = new Order();
        order.setUser(cartList.get(0).getUser());
        order.setStatus(0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = df.format(new Date());
        order.setTime(dateTime);
        orderDao.saveOrder(order);
        for(Cart cart: cartList){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder_id(order.getOrder_id());
            orderItem.setBook(cart.getBook());
            orderItem.setNumber(cart.getNum());
            orderItemDao.saveItem(orderItem);
            cartDao.deleteCart(cart.getBook(),cart.getUser());
            bookDao.setBookNum(cart.getBook(),cart.getBook().getNum() - cart.getNum());
        }
    }
}
