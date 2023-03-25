package webapp.product.repository;


import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapp.product.pojo.Product;

@Repository
@Primary
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
