package webapp.product.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "PRODUCT_PIC")// JPA: specify the table this class mapping to
public class ProductPic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIC_NO")
    private Integer picNo;

    @Column(name = "PD_NO")
    private Integer pdNo;

    @Column(name = "PD_PIC")
    private Blob pdPic;
}
