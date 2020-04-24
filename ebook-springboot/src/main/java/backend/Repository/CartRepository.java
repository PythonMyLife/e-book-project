package backend.Repository;

import backend.Entity.Book;
import backend.Entity.Cart;
import backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findAllByUser_Username(String username);
    Cart findByUser_UsernameAndBook_Isbn(String username, String isbn);

    @Transactional(propagation = Propagation.MANDATORY)
    void deleteAllByBookAndUser(Book book, User user);
}
