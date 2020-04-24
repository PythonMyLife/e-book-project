package backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@SolrDocument
public class BookSolr {

    @Id
    @Field
    private String ISBN;
    @Field
    private String bookname;
    @Field
    private String author;
    @Field
    private Integer num;
    @Field
    private Double price;
    @Field
    private String detail;

    public BookSolr() {
    }

    public BookSolr(String ISBN, String bookname, String author, Integer num, Double price, String detail) {
        this.ISBN = ISBN;
        this.bookname = bookname;
        this.price = price;
        this.author = author;
        this.num = num;
        this.detail = detail;
    }

    public BookSolr(Book book) {
        this.ISBN = book.getIsbn();
        this.bookname = book.getBookname();
        this.author = book.getAuthor();
        this.num = book.getNum();
        this.price = book.getPrice();
        this.detail = book.getDetail();
    }

    public String getIsbn(){return ISBN;}
    public void setIsbn(String ISBN){this.ISBN = ISBN;}


    public String getBookname(){return bookname;}
    public void setBookname(String bookname) { this.bookname = bookname; }


    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author = author;}


    public Integer getNum(){return num;}
    public void setNum(Integer num){this.num = num;}


    public Double getPrice(){return price;}
    public void setPrice(Double price){this.price = price;}


    public String getDetail(){return detail;}
    public void setDetail(String detail){ this.detail = detail; }

}
