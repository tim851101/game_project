package webapp.product.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "PRODUCT_AD")
public class ProductAd {

    @Id
    @Column(name = "AD_NO")
    private Integer adNo;

    @Column(name = "PD_NO")
    private Integer pdNo;

    @Column(name = "UPDATE_DATE")
    private Date updateTime;


}
