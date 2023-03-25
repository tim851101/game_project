package webapp.product.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "PIC_NO")
    private Integer picNo;

    @Column(name = "PD_NO")
    private Integer pdNO;

    @Column(name = "PD_PIC")
    private Blob pdPic;
}
