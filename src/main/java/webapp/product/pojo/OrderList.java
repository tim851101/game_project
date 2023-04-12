package webapp.product.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERLIST")
@IdClass(OrderListPK.class)
public class OrderList {
    @Id
    @Column(name = "ORD_NO")
    private Integer ordNo;
    @Id
    @Column(name = "PD_NO")
    private Integer pdNo;
    @Column(name = "QTY")
    private Integer qty;
    @Column(name = "PRICE")
    private Integer price;
}
