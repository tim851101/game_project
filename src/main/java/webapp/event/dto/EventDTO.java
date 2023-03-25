package webapp.event.dto;

import lombok.Data;
import java.sql.Date;
import java.sql.Time;

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
    private Date signupStartTime;
    private Date signupDeadline;

    private Date eventPic;


}
