package backend.DaoImpl;


import backend.Dao.BookDao;
import backend.Entity.Book;
import backend.Entity.BookSolr;
import backend.Repository.BookRepository;
import backend.util.RedisUtil;
import com.alibaba.fastjson.JSONArray;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void save(Book book){
        bookRepository.save(book);
        BookSolr bookSolr = new BookSolr(book);
        try {
            solrClient.addBean(bookSolr);
            solrClient.commit();
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
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
        Book book = null;
        System.out.println("============= Searching Book :" + isbn + "in Redis =============");
        Object b = redisUtil.get("book" + isbn);
        if(b == null) {
            System.out.println("Book: " + isbn + "is not in Redis");
            System.out.println("============= Searching Book :" + isbn + "in DB =============");
            book = bookRepository.findByIsbn(isbn);
            if(book == null) {
                System.out.println("Book: " + isbn + "is not in DB");
                return new Book();
            } else {
                redisUtil.set("book" + isbn, JSONArray.toJSON(book));
            }
        } else {
            book = JSONArray.parseObject(b.toString(), Book.class);
            System.out.println("Book: " + isbn + "is in Redis");
        }
        return book;
    }

    @Override
    public Boolean addBook(Book book){
        bookRepository.save(book);
        BookSolr bookSolr = new BookSolr(book);
        try {
            solrClient.addBean(bookSolr);
            solrClient.commit();
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void deleteBook(String isbn){
        Object b = redisUtil.get("book" + isbn);
        if(b != null) {
            redisUtil.del("book" + isbn);
        }
        bookRepository.deleteByIsbn(isbn);
        try{
            solrClient.deleteById(isbn);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBookNum(Book book, Integer num){
        Object b = redisUtil.get("book" + book.getIsbn());
        if(b != null) {
            book.setNum(num);
            redisUtil.set("book" + book.getIsbn(), JSONArray.toJSON(book));
        }
        book.setNum(num);
        bookRepository.save(book);
        BookSolr bookSolr = new BookSolr(book);
        try {
            solrClient.addBean(bookSolr);
            solrClient.commit();
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BookSolr> queryAll(String query) {
        List<BookSolr> bookSolrs = new ArrayList<>();
        SolrQuery solrQuery = new SolrQuery("detail:*" + query + "*");
        solrQuery.setSort("ISBN", SolrQuery.ORDER.asc);
        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            if(queryResponse != null) {
                bookSolrs = queryResponse.getBeans(BookSolr.class);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        for(int i = 0; i < bookSolrs.size(); i++) {
            if(bookSolrs.get(i).getNum().equals(0)) {
                bookSolrs.remove(i);
            }
        }
        return bookSolrs;
    }
}
