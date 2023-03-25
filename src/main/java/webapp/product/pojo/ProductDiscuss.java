package webapp.product.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "PRODUCT_DISCUSS")// JPA: specify the table this class mapping to
public class ProductDiscuss {

    @Id
    @Column(name = "DIS_NO")
    private Integer disNo;

    @Column(name = "PD_NO")
    private Integer pdNo;

    @Column(name = "MEM_NO")
    private Integer memNo;

    @Column(name = "DISCUSS_DESC")
    private String discussDesc;

    @Column(name = "DESC_TIME")
    private Timestamp discTime;
}
