package backend.DaoImpl;

import backend.Dao.UserDao;
import backend.Entity.User;
import backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user){
        userRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(String username) {
        return userRepository.deleteByUsername(username);
    }

    @Override
    public User findOne(String username) {
        return userRepository.getOne(username);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
