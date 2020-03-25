package backend.Dao;

import backend.Entity.Book;
import backend.Entity.Cart;
import backend.Entity.User;

import javax.transaction.Transactional;
import java.util.List;

public interface CartDao {
    //展示一个用户的所有购物车订单
    List<Cart> getUserCart(String username);

    //展示全部用户的所有购物车订单
    List<Cart> getAll();

    //得到用户的isbn的购物车信息
    Cart getCartByUserAndIsbn(String username, String isbn);

    //保存购物车信息
    Boolean saveCart(Cart cart);

    //删除购物车信息
    @Transactional
    Boolean deleteCart(Book book, User user);
}
