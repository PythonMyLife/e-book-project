package backend.ServiceImpl;

import backend.Dao.BookDao;
import backend.Dao.CartDao;
import backend.Dao.UserDao;
import backend.Entity.Book;
import backend.Entity.Cart;
import backend.Entity.User;
import backend.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Scope("prototype")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Cart> getAll(){
        return cartDao.getAll();
    }


    public List<Cart> getAllUserCart(String username){
        List<Cart> cartList = cartDao.getUserCart(username);
        for(Cart cart : cartList) {
            cart.setUser(null);
        }
        return cartList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean saveCart(String username, String isbn, Integer num){
        Cart cart = new Cart();
        Book book = bookDao.findByIsbn(isbn);
        User user = userDao.findByUsername(username);
        cart.setNum(num);
        cart.setBook(book);
        cart.setUser(user);
        cartDao.deleteCart(book, user);
        cartDao.saveCart(cart);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean addCart(String username, String isbn){
        try{
            Cart cart = cartDao.getCartByUserAndIsbn(username, isbn);
            cart.setNum(cart.getNum() + 1);
            cartDao.saveCart(cart);
        }catch (Exception e){
            Cart cart = new Cart();
            Book book = bookDao.findByIsbn(isbn);
            User user = userDao.findByUsername(username);
            cart.setNum(1);
            cart.setBook(book);
            cart.setUser(user);
            cartDao.saveCart(cart);
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean deleteCart(String username, String isbn){
        Book book = bookDao.findByIsbn(isbn);
        User user = userDao.findByUsername(username);
        cartDao.deleteCart(book, user);
        return true;
    }
}
