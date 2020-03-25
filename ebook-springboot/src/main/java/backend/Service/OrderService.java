package backend.Service;

import backend.Entity.Order;

import java.util.List;

public interface OrderService {
    //得到全部订单
    List<Order> findAllOrder();

    //得到用户未被删除的订单
    List<Order> findAllOrderByUsernameAndStatus(String username);

    //用户提交订单
    Boolean submitOrder(String username);


}
