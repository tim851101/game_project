package spring.dto;

import java.sql.Time;
import lombok.Data;

@Data
public class OpenHourDTO {
    private byte week;
    private Time openTimeStart;
    private Time openTimeEnd;
}
