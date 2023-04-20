package webapp.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Blob;
@Data
@AllArgsConstructor
public class ProductPicDTO {
    private Integer picNo;
    private Integer pdNo;
    private Blob pdPic;

}
