package webapp.booking.dto;


import lombok.Data;

@Data
public class SeatDTO {
    private String date;
    private Integer hour;
    private Integer change;
}
