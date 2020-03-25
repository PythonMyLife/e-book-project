package backend.Controller;


import backend.Entity.Book;
import backend.Entity.BookMongoDB;
import backend.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebook")
public class BookController {
    @Autowired
    BookService bookService;

    @RequestMapping(value = "/validBooks", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> allValidBooks(){
        return bookService.findAllByNumValid();
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> allBooks(){
        return bookService.findAllBook();
    }

    @RequestMapping(value="/subpage", method = RequestMethod.GET)
    @ResponseBody
    public String subpage(String isbn){
        return bookService.findByIsbn(isbn).getDetail();
    }

    @RequestMapping(value="/deleteBook", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteBook(@RequestBody Map map){
        String isbn = (String) map.get("isbn");
        return bookService.deleteBookByIsbn(isbn);
    }

    @RequestMapping(value="/editBook", method = RequestMethod.POST)
    @ResponseBody
    public boolean editBook(@RequestParam MultipartFile cover, @RequestParam String isbn,
    @RequestParam String bookname, @RequestParam String author,
    @RequestParam Integer num, @RequestParam String detail,
    @RequestParam Double price) throws IOException{
        return bookService.editBook(isbn, bookname, author, detail, num, price, cover);

    }

    @RequestMapping(value="/editBookWithoutCover", method = RequestMethod.POST)
    @ResponseBody
    public boolean editBook(@RequestParam String isbn,
                            @RequestParam String bookname, @RequestParam String author,
                            @RequestParam Integer num, @RequestParam String detail,
                            @RequestParam Double price) throws IOException{
        return bookService.editBook(isbn, bookname, author, detail, num, price);

    }

    @PostMapping(path="/addBook")
    @ResponseBody
    public boolean addBook(@RequestParam MultipartFile cover, @RequestParam String isbn,
                              @RequestParam String bookname, @RequestParam String author,
                              @RequestParam Integer num, @RequestParam String detail,
                              @RequestParam Double price) throws IOException {

        return bookService.addBook(cover, isbn, bookname, author, num, detail, price);
    }

    @PostMapping(path="/addBookMongo")
    @ResponseBody
    public boolean addBookMongo(@RequestParam MultipartFile cover, @RequestParam String isbn) throws IOException {
        return bookService.addBookMongo(cover, isbn);
    }

    @RequestMapping(value = "/bookMongo", method = RequestMethod.GET)
    @ResponseBody
    public BookMongoDB bookMongo(String isbn){
        return bookService.bookMongoByIsbn(isbn);
    }

}
