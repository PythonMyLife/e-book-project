package backend.Dao;

import backend.Entity.BookMongoDB;

import java.util.List;

public interface BookMongoDBDao {
    //得到图片
    BookMongoDB findByIsbn(String isbn);

    //增加图片
    void save(BookMongoDB bookMongoDB);
}
