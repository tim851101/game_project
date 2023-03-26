package webapp.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.product.pojo.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Boolean existsByPdNoAndPdName (Integer pdNo, String pdName);
    @Modifying
    @Transactional
    @Query("UPDATE Product e SET e.pdStatus = :status WHERE e.pdNo = :id")
    void updateProductStatus(@Param("id") Integer id, @Param("status") Boolean status);
}
