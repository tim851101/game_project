package webapp.product.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webapp.product.dto.ProductPicDTO;
import webapp.product.pojo.ProductPic;
import webapp.product.repository.ProductPicRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/pic")
public class ProductPicController {

    @Autowired
    private ProductPicRepository productPicRepository;

    @PostMapping("/product-pic")
    public ResponseEntity<String> uploadProductPic(@RequestParam("files") MultipartFile[] files,
                                                   @RequestParam("pdNo") Integer pdNo) throws IOException, SQLException {

        for (MultipartFile file : files) {
            // 檢查是否為圖片文件
            if (!file.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("請勿上傳除圖片檔以外的檔案");
            }

            // 儲存圖片到資料庫
            ProductPic productPic = new ProductPic();
            productPic.setPdNo(pdNo);
            productPic.setPdPic(new SerialBlob(file.getBytes()));
            productPicRepository.save(productPic);
        }

        return ResponseEntity.ok("圖片存進資料庫成功");
    }
    @GetMapping("/getimage" )
    public ResponseEntity<byte[]> getimgage(Integer picno) throws SQLException, IOException {
        try {

            ProductPic productPic=productPicRepository.getReferenceById(picno);


            InputStream inputStream=productPic.getPdPic().getBinaryStream();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096]; // 4KB buffer size
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        }catch (Exception e){
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            String imgFile ="C:\\Users\\Tibame_T14\\Desktop\\test\\CGA106G1\\src\\main\\resources\\static\\foreground\\static\\image\\none.jpg";
            InputStream inputStream=new FileInputStream(imgFile);;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] bytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        }
    }
    @GetMapping("/getPicDTOByPdNo")
    public List<ProductPicDTO> getPicByPdNo(Integer pdNo){
        return productPicRepository.findAllByPdNo(pdNo);
    }





}
