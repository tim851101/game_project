package webapp.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.product.dto.BackOrdersDTO;
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

@GetMapping("/getAllOrdJoinMemName")
    public List<BackOrdersDTO> getAllOrdersJoinMemName() {return ordersService.findAllJoinMemName();}
@GetMapping("/getOne")
    public OrdersDTO getOneByOrdNo(Integer ordNo){
        return ordersService.getOneByOrdNo(ordNo);
    }

    @PostMapping ("/save")
    @ResponseBody
    public Integer saveOrder(@RequestBody OrdersDTO ordersDTO){
    ordersService.saveOrders(ordersDTO);
        return 87;
    }

    @GetMapping("/updateOrdState")
    public void updateOrdStateByOrdNo(Integer ordNo, Integer ordStatus){
        ordersService.updateOrdStateByOrdNo(ordNo, ordStatus);
    }

}
