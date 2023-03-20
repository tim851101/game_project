package webapp.booking.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ReservationPriceDTO {

    private Integer reservationPriceNo;
    private Integer reservationPrice;
    private LocalDate timeStart;
    private LocalDate timeEnd;
}
