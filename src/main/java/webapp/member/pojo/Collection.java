package webapp.member.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COLLECTION")
@IdClass(CollectionPK.class)
public class Collection {

    @Id
    @Column(name = "MEM_NO")
    public Integer memNo;

    @Id
    @Column(name = "PD_NO")
    public Integer pdNo;
}
