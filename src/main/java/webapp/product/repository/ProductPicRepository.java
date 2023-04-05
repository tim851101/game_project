package webapp.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webapp.product.pojo.ProductPic;

import java.util.List;

@Repository
public interface ProductPicRepository extends JpaRepository<ProductPic, Integer> {

    @Query(value = "SELECT * FROM PRODUCT_PIC", nativeQuery = true)
    List<ProductPic> findAllProductPics();
}
