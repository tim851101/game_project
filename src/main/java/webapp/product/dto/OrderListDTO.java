package webapp.product.dto;

import lombok.Data;

@Data
public class OrderListDTO {
    private Integer ordNo;
    private Integer pdNo;
    private Integer qty;
    private Integer price;
}
