package webapp.product.dto;

import lombok.Data;

import java.sql.Date;
@Data
public class OrdersDTO {
    private Integer ordNo;
    private Integer memNo;
    private Integer useCoupon;
    private Integer ordFee;
    private Integer ordStatus;
    private Date ordCreate;
    private Integer ordPick;
    private Integer ordPayStatus;
    private Date ordFinish;
    private	Integer totalAmount;
    private Integer actualAmount;
    private String recipient;
    private String recipientAddres;
    private String recipientPh;
}
