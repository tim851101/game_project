package webapp.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.product.dto.BackOrdersDTO;
import webapp.product.pojo.Orders;

import java.util.List;


@Repository
public interface OrdersRepository extends JpaRepository <Orders, Integer> {
    Orders findByOrdNo(Integer ordNo);
    List<Orders> findAllByMemNo(Integer memNo);
@Query("SELECT new webapp.product.dto.BackOrdersDTO (o.ordNo, m.memName, o.ordCreate,o.actualAmount,o.ordPayStatus,o.ordStatus,o.ordPick,o.recipient,o.recipientAddres,o.recipientPh)" +
        " FROM Orders o JOIN Members m ON o.memNo = m.memNo")
    List<BackOrdersDTO> findAllJoinMemName();

    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET  o.ordStatus = ?2 Where o.ordNo = ?1")
    void updateOrdStateByOrdNo(Integer ordNo, Integer ordStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Orders o SET  o.ordPayStatus = ?2 Where o.ordNo = ?1")
    void updateOrdPayStateByOrdNo(Integer ordNo, Integer ordPayStatus);
    @Transactional
    @Modifying
    @Query(value = "UPDATE members m SET  coupon = coupon + (:price * 0.01) - :useCoupon Where mem_no =:memNo ", nativeQuery = true)
    void updataMemCouponBymemNo(Integer memNo , Integer price, Integer useCoupon);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET pd_stock = pd_stock-:qty WHERE pd_no = :pdNo", nativeQuery = true)
    void updataQtyByProNo(Integer pdNo , Integer qty);
}

