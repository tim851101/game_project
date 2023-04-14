package webapp.event.pojo;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
//@IdClass(EventOrdVO.class)
@IdClass(EventOrdId.class)
@Entity // JPA: mark this class as entity
@Table(name = "EVENT_ORD")// JPA: specify the table this class mapping to
public class EventOrdVO {

    //    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
    @Id // JPA: primary key
    @Column(name = "EVENT_NO")// JPA: map to column
    private int eventno;
     // JPA: primary key
//     @Id // JPA: primary key
    @Id
    @Column(name = "MEM_NO")
    private Integer memNo;

    @Column(name = "MEM_CHECKED" )
    private Integer memChecked;


    @Column(name = "MEM_NAME")
    private String memName;

    @Column(name = "MEM_ADDRESS")
    private String memAddress;

    @Column(name = "MEM_EMAIL")
    private String memEmail;

    @Column(name = "MEM_PHONE")
    private String memPhone;

    @Column(name = "EVENT_STATUS" )
    private Integer  enevtStatus;
}
class EventOrdId implements Serializable{
    private  static  final  long UID=1L;
    private Integer eventno;
    private Integer memNo;
}