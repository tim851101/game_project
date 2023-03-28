package webapp.event.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.Time;
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
    @Column(name = "EVENT_NAME", nullable = false)
    private String eventName;
    @Column(name = "EVENT_DISC")
    private String eventDisc;
    @Column(name = "EVENT_DATE")
    private Date eventDate;
    @Column(name = "EVENT_STARTTIME")
    private Time eventStarttime;
    @Column(name = "EVENT_ENDTIME")
    private Time eventEndtime;
    @Column(name = "EVENT_LIMIT")
    private Integer eventLimit;
    @Column(name = "SIGNUP_NUM", insertable = false, updatable = false)
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
    @Column(name = "SIGNUP_START_TIME")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp signupStartTime;
    @Column(name = "SIGNUP_DEADLINE")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp signupDeadline;

    @Column(name = "EVENT_PIC")
    private byte[] eventPic;
}