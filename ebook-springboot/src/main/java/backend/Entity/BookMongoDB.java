package backend.Entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Document(collection = "bookFeatures")
public class BookMongoDB {
    @Id
    private String id;
    private String isbn;
    private byte[] cover;

    public BookMongoDB(){}

    public BookMongoDB(String isbn, byte[] cover){
        this.isbn = isbn;
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        BookMongoDB that = (BookMongoDB) o;
        if(!Objects.equals(isbn, that.isbn)) return false;
        if(!Arrays.equals(cover, that.cover)) return false;

        return true;
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public int hashCode(){
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (cover != null ? Arrays.hashCode(cover) : 0);
        return result;
    }
}
