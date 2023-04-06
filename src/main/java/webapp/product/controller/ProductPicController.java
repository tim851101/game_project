package webapp.product.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webapp.product.pojo.ProductPic;
import webapp.product.repository.ProductPicRepository;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/pic")
public class ProductPicController {

    @Autowired
    private ProductPicRepository productPicRepository;

    @PostMapping("/product-pic")
    public ResponseEntity<String> uploadProductPic(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("pdNo") Integer pdNo) throws IOException, SQLException {

        // 檢查是否為圖片文件
        if (!file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("請勿上傳除圖片檔以外的檔案");
        }

        // 儲存圖片到資料庫
        ProductPic productPic = new ProductPic();
        productPic.setPdNo(pdNo);
        productPic.setPdPic(new SerialBlob(file.getBytes()));
        productPicRepository.save(productPic);

        return ResponseEntity.ok("圖片存進資料庫成功");
    }

}
