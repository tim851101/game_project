package webapp.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatDTO {
    private String date;
    private Integer hour;
    private Integer change;
}
