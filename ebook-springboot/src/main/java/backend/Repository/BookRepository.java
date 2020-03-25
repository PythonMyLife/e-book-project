package backend.Repository;


import backend.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsbn(String isbn);
    void deleteByIsbn(String isbn);
    List<Book> findAllByNumIsNot(Integer num);
    List<Book> findAllByBooknameEquals(String book);
}
