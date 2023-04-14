package webapp.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Blob;
@Data
@AllArgsConstructor
public class backgroundOrderListDTO {
    private Integer picNo;

    private String pdName;

    private Integer price;

    private Integer qty;
    private Integer totalAmount;
    private Integer ordFee;
    private Integer useCoupon;
    private Integer actualAmount;
}
