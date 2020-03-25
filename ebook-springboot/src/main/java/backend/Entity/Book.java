package backend.Entity;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books", schema = "ebook")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Book {

    private String bookname;
    private String author;
    private String cover;
    @Id
    @JsonBackReference
    @JsonManagedReference
    private String isbn;
    private Integer num;
    private Double price;
    private String detail;

    @Basic
    @Column(name = "isbn")
    public String getIsbn(){return isbn;}
    public void setIsbn(String ISBN){this.isbn = ISBN;}

    @Basic
    @Column(name = "bookname")
    public String getBookname(){return bookname;}
    public void setBookname(String bookname) { this.bookname = bookname; }

    @Basic
    @Column(name = "author")
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author = author;}

    @Basic
    @Column(name = "cover")
    public String getCover(){return cover;}
    public void setCover(String cover){this.cover = cover;}

    @Basic
    @Column(name = "num")
    public Integer getNum(){return num;}
    public void setNum(Integer num){this.num = num;}

    @Basic
    @Column(name = "price")
    public Double getPrice(){return price;}
    public void setPrice(Double price){this.price = price;}

    @Basic
    @Column(name = "detail")
    public String getDetail(){return detail;}
    public void setDetail(String detail){ this.detail = detail; }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Book that = (Book) o;
        if(!Objects.equals(bookname, that.bookname)) return false;
        if(!Objects.equals(author, that.author)) return false;
        if(!Objects.equals(cover, that.cover)) return false;
        if(!Objects.equals(isbn, that.isbn)) return false;
        if(!Objects.equals(detail, that.detail)) return false;
        if(num != null ? num.equals(that.num):that.num != null) return false;
        if(price != null ? price.equals(that.price):that.price != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        int result = 0;
        result = 31 * result + (bookname != null ? bookname.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        return result;
    }

}