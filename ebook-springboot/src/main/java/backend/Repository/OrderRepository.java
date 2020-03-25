package backend.Repository;

import backend.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUser_Username(String username);
    List<Order> findAllByUser_UsernameAndStatusEquals(String username, Integer status);
    List<Order> findAllByStatusEquals(Integer status);

}
