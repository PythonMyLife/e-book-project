package backend.DaoImpl;

import backend.Dao.BookMongoDBDao;
import backend.Entity.BookMongoDB;
import backend.Repository.BookMongoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookMongoDBDaoImpl implements BookMongoDBDao {
    @Autowired
    private BookMongoDBRepository bookMongoDBRepository;

    @Override
    public BookMongoDB findByIsbn(String isbn){
        return bookMongoDBRepository.findByIsbn(isbn);
    }

    @Override
    public void save(BookMongoDB bookMongoDB){
        bookMongoDBRepository.save(bookMongoDB);
    }
}
