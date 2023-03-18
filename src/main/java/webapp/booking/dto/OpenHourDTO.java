package webapp.booking.dto;

import java.sql.Time;
import lombok.Data;

@Data
public class OpenHourDTO {
    private int week;
    private Time openTimeStart;
    private Time openTimeEnd;
}
