package backend.Dao;

import backend.Entity.Person;

import java.util.List;

public interface FriendDao {
    // 查找用户的好友列表
    public List<Person> getFriendList(String name);

    // 添加好友
    public Boolean addFriend(String name, String friend);

    // 删除好友
    public Boolean deleteFriend(String name, String friend);
}
