package backend.ServiceImpl;


import backend.Dao.BookDao;
import backend.Dao.BookMongoDBDao;
import backend.Entity.Book;
import backend.Entity.BookMongoDB;
import backend.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookMongoDBDao bookMongoDBDao;

    @Override
    public List<Book> findAllBook(){
        return bookDao.findAll();
    }

    @Override
    public List<Book> findAllByNumValid(){
        return bookDao.findAllByNumValid();
    }

    @Override
    public Book findByIsbn(String isbn){
        return bookDao.findByIsbn(isbn);
    }

    @Override
    public Boolean deleteBookByIsbn(String isbn){
        try{
            Book book = bookDao.findByIsbn(isbn);
            book.setNum(0);
            bookDao.save(book);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean editBook(String isbn, String bookname, String author, String detail, Integer num, Double price, MultipartFile cover){
        try{
            Book book = bookDao.findByIsbn(isbn);
            book.setBookname(bookname);
            book.setAuthor(author);
            book.setDetail(detail);
            book.setNum(num);
            book.setPrice(price);
            bookDao.save(book);
            addBookMongo(cover, isbn);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean editBook(String isbn, String bookname, String author, String detail, Integer num, Double price){
        try{
            Book book = bookDao.findByIsbn(isbn);
            book.setBookname(bookname);
            book.setAuthor(author);
            book.setDetail(detail);
            book.setNum(num);
            book.setPrice(price);
            bookDao.save(book);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean addBookMongo(MultipartFile cover, String isbn){
        try{
            BookMongoDB bookMongoDB = bookMongoDBDao.findByIsbn(isbn);
            bookMongoDB.setCover(cover.getBytes());
            bookMongoDBDao.save(bookMongoDB);
            return true;
        }catch (Exception e){
            try{
                BookMongoDB bookMongoDB = new BookMongoDB();
                bookMongoDB.setIsbn(isbn);
                bookMongoDB.setCover(cover.getBytes());
                bookMongoDBDao.save(bookMongoDB);
                return true;
            }catch (Exception ex){
                System.out.println(isbn);
                return false;
            }
        }
    }

    @Override
    public Boolean addBook(MultipartFile cover, String isbn, String bookname, String author, Integer num, String detail, Double price){
        try{
            Book book = new Book();
            book.setIsbn(isbn);
            book.setPrice(price);
            book.setNum(num);
            book.setAuthor(author);
            book.setBookname(bookname);
            book.setDetail(detail);
            book.setCover("");
            bookDao.save(book);
            return addBookMongo(cover, isbn);
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public BookMongoDB bookMongoByIsbn(String isbn){
        return bookMongoDBDao.findByIsbn(isbn);
    }
}
