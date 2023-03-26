package webapp.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class ProductDTO {

    private Integer pdNo;

    private String pdName;

    private Integer pdPrice;

    private Integer pdStock;

    private String pdDescription;

    private Boolean pdStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp pdUpdate;

}
