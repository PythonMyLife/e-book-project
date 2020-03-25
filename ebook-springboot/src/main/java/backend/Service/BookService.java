package backend.Service;

import backend.Entity.Book;
import backend.Entity.BookMongoDB;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    //得到所有的书
    List<Book> findAllBook();

    //得到所有库存不为0的书
    List<Book> findAllByNumValid();

    //通过isbn查找一本书
    Book findByIsbn(String isbn);

    //得到书图片
    BookMongoDB bookMongoByIsbn(String isbn);

    //删除书
    Boolean deleteBookByIsbn(String isbn);

    //添加书图片
    Boolean addBookMongo(MultipartFile cover, String isbn);

    //编辑书
    Boolean editBook(String isbn, String bookname, String author, String detail, Integer num, Double price, MultipartFile cover);

    //编辑书
    Boolean editBook(String isbn, String bookname, String author, String detail, Integer num, Double price);

    //添加书
    Boolean addBook(MultipartFile cover, String isbn, String bookname, String author, Integer num, String detail, Double price);
}
