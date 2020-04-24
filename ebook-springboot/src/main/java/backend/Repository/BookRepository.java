package backend.Repository;


import backend.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
    @Transactional(propagation = Propagation.MANDATORY)
    void deleteByIsbn(String isbn);
    List<Book> findAllByNumIsNot(Integer num);
    List<Book> findAllByBooknameEquals(String book);
}
