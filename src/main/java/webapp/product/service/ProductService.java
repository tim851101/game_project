package webapp.product.service;

import webapp.product.dto.ProductDTO;
import webapp.product.dto.ProductLoginDTO;

public interface ProductService {
    Boolean loginCheck(ProductLoginDTO productLoginDTO);

    Boolean saveProduct(ProductDTO productDTO);

    ProductDTO findById(Integer id);

    Boolean updateStatus(Integer id, Boolean status);
}
