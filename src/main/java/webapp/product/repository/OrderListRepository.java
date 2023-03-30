package webapp.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.product.pojo.OrderList;

import java.util.List;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Integer> {
    List<OrderList> findAllByOrdNo (Integer OrdNo);
}
