package webapp.event.dto;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class EventOrdDTO {
    private int eventno;
    private int memNo;
    private int memChecked;
    private String memName;
    private String memAddress;
    private String memEmail;
    private String memPhone;
    private int  enevtStatus;
}
