package webapp.event.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "EVENT")// JPA: specify the table this class mapping to
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_NO",insertable = false)
    private Integer eventNo;
    @Column(name = "EVENT_NAME")
    private String eventName;
    @Column(name = "EVENT_DISC")
    private String eventDisc;
    @Column(name = "EVENT_DATE")
    private Date eventDate;
    @Column(name = "EVENT_STARTTIME")
    private Timestamp eventStarttime;
    @Column(name = "EVENT_ENDTIME")
    private Timestamp eventEndtime;
    @Column(name = "EVENT_LIMIT")
    private Integer eventLimit;
    @Column(name = "SIGNUP_NUM")
    private Integer signupNum;
    @Column(name = "EVENT_FEE")
    private Integer eventFee;
    @Column(name = "EVENT_WINNER1", insertable = false)
    private String eventWinner1;
    @Column(name = "EVENT_WINNER2", insertable = false)
    private String eventWinner2;
    @Column(name = "EVENT_WINNER3", insertable = false)
    private String eventWinner3;
    @Column(name = "EVENT_STATUS")
    private Byte eventStatus;
    @Column(name = "SIGNUP_START_TIME",updatable = false)
    private Timestamp signupStartTime;
    @Column(name = "SIGNUP_DEADLINE",updatable = false)
    private Timestamp signupDeadline;

    @Column(name = "EVENT_PIC")
    private byte[] eventPic;
}