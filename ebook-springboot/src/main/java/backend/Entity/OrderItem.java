package backend.Entity;


import com.fasterxml.jackson.annotation.*;
import io.swagger.models.auth.In;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orderitems", schema = "ebook")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class OrderItem {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @JsonBackReference
    @JsonManagedReference
    private Integer item_id;

    @Basic
    @Column(name = "order_id")
    private Integer order_id;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    private Book book;

    @Basic
    @Column(name = "number")
    private Integer number;

    public Integer getItem_id(){ return item_id; }
    public void setItem_id(Integer item_id){ this.item_id = item_id; }

    public Integer getOrder_id(){ return order_id; }
    public void setOrder_id(Integer order_id){ this.order_id = order_id; }

    public Book getBook(){ return book; }
    public void setBook(Book book){ this.book = book; }

    public Integer getNumber(){ return number; }
    public void setNumber(Integer number) { this.number = number; }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        OrderItem that = (OrderItem) o;
        if(!Objects.equals(item_id, that.item_id)) return false;
        if(!Objects.equals(order_id, that.order_id)) return false;
        if(!Objects.equals(book, that.book)) return false;
        if(number != null ? number.equals(that.number):that.number != null) return false;

        return true;
    }


    @Override
    public int hashCode(){
        int result = 0;
        result = 31 * result + (item_id != null ? item_id.hashCode() : 0);
        result = 31 * result + (order_id != null ? order_id.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
