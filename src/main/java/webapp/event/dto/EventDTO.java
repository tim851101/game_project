package webapp.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class EventDTO {
    private Integer eventNo;
    private String eventName;
    private String eventDisc;
    private Date eventDate;
    private Time eventStarttime;
    private Time eventEndtime;
    private Integer eventLimit;
    private Integer signupNum;
    private Integer eventFee;
    private String eventWinner1;
    private String eventWinner2;
    private String eventWinner3;
    private Byte eventStatus;
    private Timestamp signupStartTime;
    private Timestamp signupDeadline;

    private byte[] eventPic;


}
