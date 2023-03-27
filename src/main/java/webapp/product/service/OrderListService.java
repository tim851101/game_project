package webapp.product.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.product.dto.OrderListDTO;
import webapp.product.pojo.OrderList;
import webapp.product.repository.OrderListRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderListService {
    @Autowired
    private OrderListRepository orderListRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<OrderListDTO> getAllByOrdNo(Integer ordNo){
        return orderListRepository.findAllByOrdNo(ordNo)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }



    private OrderListDTO EntityToDTO(OrderList orderList) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        OrderListDTO orderListDTO = new OrderListDTO();
        orderListDTO = modelMapper.map(orderList, OrderListDTO.class);
        return orderListDTO;
    }
}
