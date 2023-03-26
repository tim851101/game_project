package webapp.product.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.pojo.Employee;
import webapp.product.dto.ProductDTO;
import webapp.product.dto.ProductLoginDTO;
import webapp.product.pojo.Product;
import webapp.product.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    final ModelMapper modelMapper;
    final ProductRepository productRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public Boolean loginCheck(ProductLoginDTO productLoginDTO) {
        return productRepository.existsByPdNoAndPdName(productLoginDTO.getPdNo(), productLoginDTO.getPdName());
    }

    @Override
    public Boolean saveProduct(ProductDTO dto) {
        try {
            productRepository.save(modelMapper.map(dto, Product.class));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductDTO findById(Integer id) {
        System.out.println(productRepository.findById(id));
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), ProductDTO.class);
        } else {
            return modelMapper.map(new Product(), ProductDTO.class);
        }
    }

    @Override
    public Boolean updateStatus(Integer id, Boolean status) {
        try {
            productRepository.updateProductStatus(id, status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
