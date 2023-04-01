package webapp.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.product.dto.OrderListDTO;
import webapp.product.dto.OrdersDTO;
import webapp.product.dto.backgroundOrderListDTO;
import webapp.product.service.OrderListService;

import java.util.List;

@RestController
@RequestMapping("/ordList")
public class OrderListController {
    @Autowired
    private OrderListService orderListService;

    @GetMapping("/getAllByordNo")
    public List<OrderListDTO> getAllByOrdNo(Integer ordNo) {
        return orderListService.getAllByOrdNo(ordNo);
    }

    @PostMapping("/save")
    @ResponseBody
    public void saveOrderList(@RequestBody OrderListDTO orderListDTO ){
        orderListService.saveOrderList(orderListDTO);
    }

    @GetMapping("/getBackgroundOrderListByOrdNo")
    public List<backgroundOrderListDTO> findListByOrdNo(Integer ordNo) {
        return orderListService.findListByOrdNo(ordNo);
    }
}