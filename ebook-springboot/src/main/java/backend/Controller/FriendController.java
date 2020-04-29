package backend.Controller;

import backend.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    @ResponseBody
    public List<String> findUserByUsernameContains(String name) {
        return friendService.findUserByUsernameContains(name);
    }

    @RequestMapping(value = "/getFriendList", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getFriendList(String name) {
        return friendService.getFriendList(name);
    }

    @RequestMapping(value = "/addFriend", method = RequestMethod.GET)
    @ResponseBody
    public Boolean addFriend(String name, String friend) {
        return friendService.addFriend(name, friend);
    }

    @RequestMapping(value = "/deleteFriend", method = RequestMethod.GET)
    @ResponseBody
    public Boolean deleteFriend(String name, String friend) {
        return friendService.deleteFriend(name, friend);
    }
}
