package backend.ServiceImpl;

import backend.Dao.BookDao;
import backend.Entity.Book;
import backend.Service.RmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RmiServiceImpl implements RmiService{

    @Autowired
    private BookDao bookDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Map<String, Object>> getDetail(String book) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Book> books = bookDao.findAllByBooknameEquals(book);
        for(Book oneBook : books) {
            Map<String, Object> map = new HashMap<>();
            map.put("bookname", oneBook.getBookname());
            map.put("author", oneBook.getAuthor());
            map.put("detail", oneBook.getDetail());
            map.put("isbn", oneBook.getIsbn());
            map.put("price", oneBook.getPrice());
            result.add(map);
        }
        return result;
    }

}
