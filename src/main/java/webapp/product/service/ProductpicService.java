package webapp.product.service;

import webapp.product.dto.ProductPicDTO;

import java.sql.Blob;

public interface ProductpicService {
    Blob saveProductPic(ProductPicDTO productPicDTO);
}
