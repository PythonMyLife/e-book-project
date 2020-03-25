package backend.Repository;


import backend.Entity.BookMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookMongoDBRepository extends MongoRepository<BookMongoDB, String> {
    BookMongoDB findByIsbn(String isbn);
}
