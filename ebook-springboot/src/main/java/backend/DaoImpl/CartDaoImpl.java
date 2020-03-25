package backend.DaoImpl;

import backend.Dao.CartDao;
import backend.Entity.Book;
import backend.Entity.Cart;
import backend.Entity.User;
import backend.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getUserCart(String username){
        return cartRepository.findAllByUser_Username(username);
    }

    @Override
    public List<Cart> getAll(){
        return cartRepository.findAll();
    }

    @Override
    public Boolean saveCart(Cart cart){
        try{
            cartRepository.save(cart);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public Boolean deleteCart(Book book, User user){
        try{
            cartRepository.deleteAllByBookAndUser(book, user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Cart getCartByUserAndIsbn(String username, String isbn){
        return cartRepository.findByUser_UsernameAndBook_Isbn(username, isbn);
    }
}
