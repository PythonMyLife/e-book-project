package backend.ServiceImpl;

import backend.Dao.UserDao;
import backend.Entity.User;
import backend.Service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User addUser(User user) {
        user.setStatus(0);
        user.setIdentity(0);
        user.setPassword(user.getPassword());
        userDao.addUser(user);
        return userDao.findOne(user.getUsername());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findUserByUsername(String username) {
        return userDao.findOne(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteUser(String username) {
        return userDao.deleteUser(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User updateUser(Map<String, String> data) {
        String username = data.get("username");
        User user = userDao.findOne(username);
        String email = data.get("email"), password = data.get("password"), status = data.get("status");
        if(email != null) {
            user.setEmail(email);
        }
        if(password != null) {
            user.setPassword(password);
        }
        if(status != null) {
            user.setStatus(Integer.valueOf(status));
        }
        return userDao.addUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean nameIsValid(String username) {
        if(userDao.findByUsername(username)== null) return true;
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean checkPassword(User user) {
        try{
            User userFind = userDao.findOne(user.getUsername());
            if(user.getPassword().equals(userFind.getPassword())) {
                return true;
            }else {
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer checkStatusAndIdentity(String username){
        User userFind = userDao.findOne(username);
        if(userFind.getStatus().equals(1)){
            return 2;
        }else if(userFind.getIdentity().equals(1)){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<User> findAll(){
        return userDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<User> findAllUser(){
        List<User> userList = userDao.findAll();
        ArrayList<User> users = new ArrayList<>();
        for(User user : userList){
            if(user.getIdentity() != 1){
                users.add(user);
            }
        }
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean changeStatus(String username){
        try{
            User user = userDao.findByUsername(username);
            user.setStatus(1 - user.getStatus());
            userDao.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean regist(String username, String password, String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setStatus(0);
        user.setIdentity(0);
        if(nameIsValid(username)){
            addUser(user);
            return true;
        }else{
            return false;
        }
    }
}