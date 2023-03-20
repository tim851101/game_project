package webapp.member.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "MEM_PERMISSION")
public class MemberPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERMISSIONS_NO")
    private Integer permissionsNo;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "LOWER_LIMIT")
    private Integer lowerLimit;

    @Column(name = "UPPER_LIMIT")
    private Integer upperLimit;
}
