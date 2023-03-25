package webapp.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapp.product.dto.ProductDTO;
import webapp.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/Prod")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/per")
    public List<ProductDTO> getAllProduct(){
        return productService.getAllDTO();
    }
}
