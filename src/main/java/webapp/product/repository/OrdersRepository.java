package webapp.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.product.pojo.Orders;


@Repository
public interface OrdersRepository extends JpaRepository <Orders, Integer> {
    Orders findByOrdNo(Integer ordNo);


    @Transactional
    @Modifying
    @Query("update Orders o set  o.ordStatus = ?2 where o.ordNo = ?1")
    void updateOrdStateByOrdNo(Integer ordNo, Integer ordStatus);
}

