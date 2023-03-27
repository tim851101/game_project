package webapp.product.pojo;

import lombok.Data;

import java.io.Serializable;
@Data
public class OrderListPK implements Serializable {
    public Integer ordNo;
    public Integer pdNo;
}
