package webapp.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.product.pojo.Orders;


@Repository
public interface OrdersRepository extends JpaRepository <Orders, Integer> {
    Orders findByOrdNo(Integer ordNo);
}

