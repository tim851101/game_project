package webapp.product.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class BackOrdersDTO {
    private Integer ordNo;
    private String memName;
    private Date ordCreate;
    private Integer actualAmount;
    private Integer ordPayStatus;
    private Integer ordStatus;
    private Integer ordPick;
    private String recipient;
    private String recipientAddres;
    private String recipientPh;
}
