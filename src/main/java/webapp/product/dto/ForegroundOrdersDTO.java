package webapp.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForegroundOrdersDTO {
    private Integer ordNo;
    private Integer memNo;
    private Date ordCreate;
    private Integer actualAmount;
    private Integer ordPayStatus;
    private Integer ordStatus;
    private Integer ordPick;

}
