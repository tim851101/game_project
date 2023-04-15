package webapp.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webapp.product.dto.BackOrdersDTO;
import webapp.product.dto.ProductPicDTO;
import webapp.product.pojo.ProductPic;

import java.util.List;

@Repository
public interface ProductPicRepository extends JpaRepository<ProductPic, Integer> {
    @Query("SELECT new webapp.product.dto.ProductPicDTO (p.picNo,p.pdNo)" +
            " FROM ProductPic p WHERE p.pdNo = :pdNo")
    List<ProductPicDTO> findAllByPdNo(Integer pdNo);
}
