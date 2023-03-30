package webapp.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.product.dto.ProductPicDTO;
import webapp.product.pojo.ProductPic;
import webapp.product.service.ProductpicService;

import java.sql.Blob;
import java.util.List;

@Controller
@RequestMapping("/productpic")
public class ProductpicController {
    private final ProductpicService productpicService;
    public ProductpicController(ProductpicService productpicService) {
        this.productpicService = productpicService;
    }

    @GetMapping("/get-all")
    @ResponseBody
    public List<ProductPic> getallProductPic(){
        return null;
    }

    @PostMapping("/save-productpic")
    public Blob saveProductpic(@RequestBody ProductPicDTO productPicDTO){
        return productpicService.saveProductPic(productPicDTO);
    }
}
