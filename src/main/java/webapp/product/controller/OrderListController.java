package webapp.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapp.product.dto.OrderListDTO;
import webapp.product.dto.OrdersDTO;
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



}