package webapp.product.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.product.dto.BackOrdersDTO;
import webapp.product.dto.OrdersDTO;
import webapp.product.dto.ForegroundOrdersDTO;
import webapp.product.pojo.Orders;
import webapp.product.repository.OrdersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {
@Autowired
    private OrdersRepository ordersRepository;
@Autowired
    private ModelMapper modelMapper;

    public List<OrdersDTO> getAllOrders(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return ordersRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public List<BackOrdersDTO> findAllJoinMemName(){
        return ordersRepository.findAllJoinMemName();
    }

    public Integer saveOrders(OrdersDTO ordersDTO){
        Orders orders = modelMapper.map(ordersDTO,Orders.class );
        orders.setOrdCreate((new java.sql.Date(System.currentTimeMillis())));
        orders.setOrdFinish(null);
        orders.setOrdPayStatus(0);
        orders.setOrdStatus(0);
        orders = ordersRepository.save(orders);
        return orders.getOrdNo();
    }

    public OrdersDTO getOneByOrdNo(Integer ordNo){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return EntityToDTO(ordersRepository.findByOrdNo(ordNo));
    }

    public void updateOrdStateByOrdNo(Integer ordNo, Integer ordStatus){
        ordersRepository.updateOrdStateByOrdNo(ordNo, ordStatus);
    }

    public void updateOrdPayStateByOrdNo(Integer ordNo, Integer ordPayStatus){
        ordersRepository.updateOrdPayStateByOrdNo(ordNo, ordPayStatus);
    }

    public List<ForegroundOrdersDTO> findAllByMemNo(Integer memNo){
//        List<Orders> list = ordersRepository.findAllByMemNo(memNo);
//        List<ForegroundOrdersDTO> list2= new ArrayList<ForegroundOrdersDTO>();
//        for(Orders item : list){
//            ForegroundOrdersDTO foregroundOrdersDTO = modelMapper.map(item, ForegroundOrdersDTO.class);
//            list2.add(foregroundOrdersDTO);
//        }
//        return list2;
          return ordersRepository.findAllByMemNo(memNo)
                .stream()
                .map(orders -> modelMapper.map(orders,ForegroundOrdersDTO.class))
                .collect(Collectors.toList());
    }

    public void updatememCoupon(Integer memNo, Integer price, Integer useCoupon){
            ordersRepository.updataMemCouponBymemNo(memNo,price,useCoupon);
    };

    public void updateProQty(Integer pdNo,Integer reduceQty){
        ordersRepository.updataQtyByProNo(pdNo,reduceQty);
    }


    private OrdersDTO EntityToDTO(Orders orders) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = modelMapper.map(orders, OrdersDTO.class);
        return ordersDTO;
    }


}
