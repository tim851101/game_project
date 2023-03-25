package webapp.product.service;

import core.service.BasicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webapp.product.dto.ProductDTO;
import webapp.product.pojo.Product;
import webapp.product.repository.ProductRepository;


@Service
public class  ProductService extends BasicService<ProductRepository, Product, ProductDTO> {

    public ProductService(ModelMapper modelMapper, ProductRepository productRepository) {
        super(modelMapper, productRepository);
    }
}