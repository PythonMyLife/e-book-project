package backend.Dao;

import backend.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.common.base.Optional;

import java.util.List;

public interface UserDao {
    void save(User user);
    //增加user
    User addUser(User user);

    //删除user
    boolean deleteUser(String username);

    //通过username查找user
    User findOne(String username);
    User findByUsername(String username);

    //查找全部user
    List<User> findAll();
}
