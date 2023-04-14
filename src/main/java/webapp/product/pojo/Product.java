package webapp.product.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "PRODUCT")// JPA: specify the table this class mapping to
//對應MySQL自己負責的表格
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PD_NO")
    private Integer pdNo;

    @Column(name = "PD_NAME")
    private String pdName;

    @Column(name = "PD_PRICE")
    private Integer pdPrice;

    @Column(name = "PD_STOCK")
    private Integer pdStock;

    @Column(name = "PD_DESCRIPTION")
    private String pdDescription;

    @Column(name = "PD_STATUS")
    private Boolean pdStatus;

    @Column(name = "PD_UPDATE")
    private Timestamp pdUpdate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "PD_NO",referencedColumnName = "PD_NO")
    private List<ProductPic> productPics;

}
