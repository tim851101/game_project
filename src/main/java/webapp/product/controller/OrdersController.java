package webapp.product.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.member.service.MemberService;
import webapp.product.dto.*;
import webapp.product.service.OrdersService;
import webapp.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/ord")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public List<OrdersDTO> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/getAllByMemNo")
    public List<ForegroundOrdersDTO> findAllByMemNo(Integer memNo) {
        return ordersService.findAllByMemNo(memNo);
    }

    @GetMapping("/getAllOrdJoinMemName")
    public List<BackOrdersDTO> getAllOrdersJoinMemName() {
        return ordersService.findAllJoinMemName();
    }

    @GetMapping("/getOne")
    public OrdersDTO getOneByOrdNo(Integer ordNo) {
        return ordersService.getOneByOrdNo(ordNo);
    }

    @PostMapping("/save")
    @ResponseBody
    public Integer saveOrder(@RequestBody @Valid OrdersDTO ordersDTO) {
        try {
            return ordersService.saveOrders(ordersDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @PostMapping("/updateOrdState")
    @ResponseBody
    public void updateOrdStateByOrdNo(@RequestBody OrdersStatusDTO ordersStatusDTO) {
        try {
            ordersService.updateOrdStateByOrdNo(ordersStatusDTO.getOrdNo(), ordersStatusDTO.getOrdStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @PostMapping("/updateOrdPayState")
    @ResponseBody
    public void updateOrdStatePayByOrdNo(@RequestBody OrdersPayStatusDTO ordersPayStatusDTO) {
        try {
            ordersService.updateOrdPayStateByOrdNo(ordersPayStatusDTO.getOrdNo(), ordersPayStatusDTO.getOrdPayStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/updateMemCoupon")
    @ResponseBody
    public Integer updateMemCoupon(@RequestBody UseCouponDTO useCouponDTO) {
        try {
            ordersService.updatememCoupon(useCouponDTO.getMemNo(), useCouponDTO.getPrice(), useCouponDTO.getUseCoupon());
            return memberService.findById(useCouponDTO.getMemNo()).getCoupon();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    @PostMapping("/updateProQty")
    @ResponseBody
    public Integer updateProQty(@RequestBody UpdateProQtyDTO updateProQtyDTO) {
        try {
            ordersService.updateProQty(updateProQtyDTO.getPdNo(),updateProQtyDTO.getReduceQty());
            return productService.findById(updateProQtyDTO.getPdNo()).getPdStock();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
