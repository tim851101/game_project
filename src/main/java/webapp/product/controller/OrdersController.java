package webapp.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.product.dto.OrdersDTO;
import webapp.product.service.OrdersService;

import java.util.List;
@RestController
@RequestMapping("/ord")
public class OrdersController {
@Autowired
    private OrdersService ordersService;
@GetMapping("/getAll")
    public List<OrdersDTO> getAllOrders() {
        return ordersService.getAllOrders();
    }
@GetMapping("/getOne")
    public OrdersDTO getOneByOrdNo(Integer ordNo){
        return ordersService.getOneByOrdNo(ordNo);
    }

@PostMapping ("/save")
    public void saveOrder(@RequestBody OrdersDTO ordersDTO){
        ordersService.saveOrders(ordersDTO);
    }
}