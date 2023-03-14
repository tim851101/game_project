package webapp.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@IdClass(Collection.CollectionId.class)
@Entity // JPA: mark this class as entity
@Table(name = "COLLECTION")
public class Collection {

    @Id
    @Column(name = "MEM_NO")
    private Integer memNo;

    @Id
    @Column(name = "PD_NO")
    private Integer pdNo;

    public static class CollectionId implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private Integer memNo;
        private Integer pdNo;
    }

}
