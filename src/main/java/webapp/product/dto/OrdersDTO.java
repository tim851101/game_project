package webapp.product.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
    @Min(value=0, message = "金額不可為負值")
    private	Integer totalAmount;

    @Min(value=0, message = "金額不可為負值")
    private Integer actualAmount;

    @NotEmpty(message = "收件人姓名請勿空白")
    private String recipient;

    @NotEmpty(message = "收件地址請勿空白")
    private String recipientAddres;

    @NotEmpty(message = "收件人電話請勿空白")
    private String recipientPh;
}
