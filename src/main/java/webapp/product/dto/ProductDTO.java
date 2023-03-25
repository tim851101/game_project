package webapp.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class ProductDTO {

    private Integer pdNo;

    private String pdName;

    private Integer pdPrice;

    private Integer pdStock;

    private String pdDescription;

    private Integer pdStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp pdUpdate;
}
