package webapp.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.product.dto.backgroundOrderListDTO;
import webapp.product.pojo.OrderList;

import java.util.List;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Integer> {
    List<OrderList> findAllByOrdNo (Integer OrdNo);

    @Query("SELECT new webapp.product.dto.backgroundOrderListDTO(pp.pdPic, p.pdName, ol.price, ol.qty, o.totalAmount, o.ordFee, o.useCoupon, o.actualAmount) " +
            "FROM OrderList ol " +
            "JOIN Product p ON p.pdNo = ol.pdNo " +
            "JOIN ProductPic pp ON ol.pdNo = pp.pdNO " +
            "JOIN Orders o ON ol.ordNo = o.ordNo " +
            "WHERE ol.ordNo = :ordNo " +
            "AND pp.picNo IN (SELECT MIN(p.picNo) FROM ProductPic p WHERE p.pdNO = ol.pdNo GROUP BY p.pdNO)")
    List<backgroundOrderListDTO> findListByOrdNo(@Param("ordNo") Integer ordNo);
}
