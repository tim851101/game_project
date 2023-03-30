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
@Query("SELECT new webapp.product.dto.BackOrdersDTO (o.ordNo, m.memName, o.ordCreate,o.actualAmount,o.ordPayStatus,o.ordStatus,o.ordPick,o.recipient,o.recipientAddres,o.recipientPh)" +
        " FROM Orders o JOIN Members m ON o.memNo = m.memNo")
    List<BackOrdersDTO> findAllJoinMemName();

    @Transactional
    @Modifying
    @Query("update Orders o set  o.ordStatus = ?2 where o.ordNo = ?1")
    void updateOrdStateByOrdNo(Integer ordNo, Integer ordStatus);

    @Transactional
    @Modifying
    @Query("update Orders o set  o.ordPayStatus = ?2 where o.ordNo = ?1")
    void updateOrdPayStateByOrdNo(Integer ordNo, Integer ordPayStatus);
}

