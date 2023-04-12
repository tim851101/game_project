package webapp.product.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import webapp.product.dto.ProductDTO;
import webapp.product.dto.ProductLoginDTO;
import webapp.product.pojo.Product;
import webapp.product.repository.ProductRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

//    查詢全部商品
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Integer getNextPdNo() {
        Integer maxPdNo = productRepository.findMaxPdNo();
        return maxPdNo == null ? 1 : maxPdNo + 1;
    }

//    修改商品
    @Override
    public Boolean updateProduct(ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(productDTO.getPdNo());
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setPdName(productDTO.getPdName());
            product.setPdPrice(productDTO.getPdPrice());
            product.setPdStock(productDTO.getPdStock());
            product.setPdDescription(productDTO.getPdDescription());
            product.setPdStatus(productDTO.getPdStatus());
            product.setPdUpdate(new Timestamp(System.currentTimeMillis()));
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }
//    與前端商城作回應
public List<ProductDTO> findAll() {
    List<Product> productList = productRepository.findAll();
    List<ProductDTO> productDTOList = new ArrayList<>();
    for (Product product : productList) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        productDTOList.add(productDTO);
    }
    return productDTOList;
}
}