package backend.Controller;

import backend.Entity.User;
import backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebook")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers(){
        return userService.findAllUser();
    }

    @GetMapping("/find/user/{name}")
    public User getUserByName(@PathVariable("name") String username) {
        return userService.findUserByUsername(username);
    }

    @RequestMapping(value= "/index", method = RequestMethod.POST)
    @ResponseBody
    public Integer login(@RequestBody Map map){
        String username = (String)map.get("username");
        String password = (String)map.get("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if(userService.checkPassword(user)){
            return userService.checkStatusAndIdentity(username);
        }else {
            return -1;
        }
    }

    /*@RequestMapping(value= "/regist", method = RequestMethod.GET)
    @ResponseBody
    public Integer regist(String username, String password, String email){
        //String username = (String)map.get("username");
        //String password = (String)map.get("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        //String email = (String)map.get("email");
        user.setEmail(email);
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        System.out.println(userService.nameIsValid(username));
        if(userService.nameIsValid(username)){
            userService.addUser(user);
            return 1;
        }else{
            return 0;
        }
    }*/

    /*@PostMapping("/regist")
    public Integer regist(@RequestBody User user){
        if(userService.nameIsValid(user.getUsername())) {
            userService.addUser(user);
            return 1;
        }else {
            return 0;
        }
    }*/

    @RequestMapping(value= "/regist", method = RequestMethod.POST)
    @ResponseBody
    public Integer regist(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");
        String email = map.get("email");
        return userService.regist(username, password, email)?1:0;
    }

    @RequestMapping(value= "/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public boolean changeStatus(@RequestBody Map map){
        String username = (String)map.get("username");
        return userService.changeStatus(username);
    }

}
