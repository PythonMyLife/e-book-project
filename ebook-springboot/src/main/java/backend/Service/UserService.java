package backend.Service;

import backend.Entity.User;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface UserService {
    User addUser(User user);

    User findUserByUsername(String username);

    @Transactional
    boolean deleteUser(String username);

    User updateUser(Map<String, String> data);

    boolean nameIsValid(String username);

    boolean checkPassword(User user);

    Integer checkStatusAndIdentity(String username);

    List<User> findAll();

    List<User> findAllUser();

    boolean changeStatus(String username);

    boolean regist(String username, String password, String email);


}
