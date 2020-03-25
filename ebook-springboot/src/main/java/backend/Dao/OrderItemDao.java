package backend.Dao;

import backend.Entity.OrderItem;

import java.util.List;

public interface OrderItemDao {

    //增加item
    OrderItem saveItem(OrderItem orderItem);

}
