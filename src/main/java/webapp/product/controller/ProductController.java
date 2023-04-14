package webapp.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.product.dto.ProductDTO;
import webapp.product.dto.ProductLoginDTO;
import webapp.product.pojo.Product;
import webapp.product.service.ProductService;

import java.util.List;


@Controller
@RequestMapping("/product")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/login-check")
    @ResponseBody
    public Boolean loginCheck(@RequestBody ProductLoginDTO productLoginDTO) {
        return productService.loginCheck(productLoginDTO);
    }

    @GetMapping("/ls-one")
    @ResponseBody
    public ProductDTO findById(@RequestParam Integer id) {
        return productService.findById(id);
    }
    @GetMapping("/set-status")
    @ResponseBody
    public Boolean updateStatus(@RequestParam Integer id, @RequestParam Boolean status) {
        return productService.updateStatus(id, status);
    }

//    新增商品
    @PostMapping("/save-product")
    @ResponseBody
    public Boolean saveProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @GetMapping("/find-one")
    @ResponseBody
    public ProductDTO findProdById(@RequestParam Integer id){
        return productService.findById(id);
    }

//    查詢全部商品
    @GetMapping("/get-all")
    @ResponseBody
    public List<Product> getallProduct() {
        return productService.getAllProduct();
    }

//    修改商品
    @PostMapping("/update-product")
    @ResponseBody
    public Boolean updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }
//與前端商城作回應
    @GetMapping("/get-pall")
    @ResponseBody
    public List<Product> getAllProducts() {
    return productService.getAllProduct();
}

   }
