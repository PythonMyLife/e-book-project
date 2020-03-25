package backend.DaoImpl;


import backend.Dao.BookDao;
import backend.Entity.Book;
import backend.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void save(Book book){
        bookRepository.save(book);
    }
    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllByBooknameEquals(String book) {
        return bookRepository.findAllByBooknameEquals(book);
    }

    @Override
    public List<Book> findAllByNumValid(){
        return bookRepository.findAllByNumIsNot(0);
    }

    @Override
    public Book findByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Boolean addBook(Book book){
        if(bookRepository.save(book) != null)
            return true;
        return false;
    }

    @Override
    public void deleteBook(String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    @Override
    public void setBookNum(Book book, Integer num){
        book.setNum(num);
        bookRepository.save(book);
    }
}
