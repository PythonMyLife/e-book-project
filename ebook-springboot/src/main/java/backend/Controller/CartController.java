package backend.Controller;


import backend.Entity.Cart;
import backend.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebook")
public class CartController {
    @Autowired
    WebApplicationContext applicationContext;

    @RequestMapping(value="/getcarts", method = RequestMethod.GET)
    @ResponseBody
    public List<Cart> getCarts(){
        CartService cartService = applicationContext.getBean(CartService.class);
        return cartService.getAll();
    }

    @RequestMapping(value="/savecart", method = RequestMethod.GET)
    @ResponseBody
    public Boolean saveCart(String username, String isbn, Integer num){
        CartService cartService = applicationContext.getBean(CartService.class);
        return cartService.saveCart(username, isbn, num);
    }

    @RequestMapping(value="/get_user_carts", method = RequestMethod.GET)
    @ResponseBody
    public List<Cart> getUserCarts(String username){
        CartService cartService = applicationContext.getBean(CartService.class);
        return cartService.getAllUserCart(username);
    }

    @RequestMapping(value="/addcart", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addCart(@RequestBody Map map){
        String username = (String) map.get("username");
        String isbn = (String) map.get("isbn");
        CartService cartService = applicationContext.getBean(CartService.class);
        return cartService.addCart(username, isbn);
    }

    @RequestMapping(value="/deletecart", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteCart(@RequestBody Map map){
        String username = (String) map.get("username");
        String isbn = (String) map.get("isbn");
        CartService cartService = applicationContext.getBean(CartService.class);
        return cartService.deleteCart(username, isbn);
    }
}
