package webapp.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
public class memofeventDTO {
    private Integer eventNo;
    private String eventName;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Timestamp eventStarttime;
    private Integer eventFee;
    private Integer memNo;

}
