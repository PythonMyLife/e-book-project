package backend.Service;

import backend.Entity.Cart;

import java.util.List;

public interface CartService {
    //得到全部用户的购物车信息
    List<Cart> getAll();

    //得到username用户的购物车信息
    List<Cart> getAllUserCart(String username);

    //保存cart信息
    Boolean saveCart(String username, String isbn, Integer num);

    //加入购物车
    Boolean addCart(String username, String isbn);

    //删除购物车信息
    Boolean deleteCart(String username, String isbn);
}
