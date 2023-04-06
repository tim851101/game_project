package webapp.product.dto;

import lombok.Data;

import java.sql.Blob;
@Data
public class ProductPicDTO {
    private Integer picNo;
    private Integer pdNo;
    private Blob pdPic;

}
