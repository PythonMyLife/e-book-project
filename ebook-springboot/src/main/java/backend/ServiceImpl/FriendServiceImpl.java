package backend.ServiceImpl;

import backend.Dao.FriendDao;
import backend.Dao.UserDao;
import backend.Entity.Person;
import backend.Entity.User;
import backend.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<String> findUserByUsernameContains(String name) {
        List<User> userList = userDao.findAllByUsernameContains(name);
        List<String> result = new ArrayList<>();
        for(User u : userList) {
            result.add(u.getUsername());
        }
        return result;
    }

    @Override
    public List<String> getFriendList(String name) {
        if(userDao.findByUsername(name) == null) {
            return new ArrayList<>();
        }
        List<Person> friendList = friendDao.getFriendList(name);
        List<String> result = new ArrayList<>();
        for(Person p : friendList) {
            result.add(p.getName());
        }
        return result;
    }

    @Override
    public Boolean addFriend(String name, String friend) {
        if(userDao.findByUsername(name) == null || userDao.findByUsername(friend) == null) {
            return false;
        }
        return friendDao.addFriend(name, friend);
    }

    @Override
    public Boolean deleteFriend(String name, String friend) {
        if(userDao.findByUsername(name) == null || userDao.findByUsername(friend) == null) {
            return false;
        }
        return friendDao.deleteFriend(name, friend);
    }
}
