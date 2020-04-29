package backend.Service;

import java.util.List;

public interface FriendService {
    // 查找好友
    public List<String> findUserByUsernameContains(String name);

    // 查找用户的好友列表
    public List<String> getFriendList(String name);

    // 添加好友
    public Boolean addFriend(String name, String friend);

    // 删除好友
    public Boolean deleteFriend(String name, String friend);
}
