package backend.Controller;

import backend.Entity.Order;
import backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value="/getorders", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> getOrders(){
        return orderService.findAllOrder();
    }

    @RequestMapping(value="/getuserorder",method = RequestMethod.GET)
    @ResponseBody
    public List<Order> getUserOrder(String username){
        return orderService.findAllOrderByUsernameAndStatus(username);
    }

    @RequestMapping(value="/submitorder", method = RequestMethod.GET)
    @ResponseBody
    public Boolean submitOrder(String username){
        return orderService.submitOrder(username);
    }
}
