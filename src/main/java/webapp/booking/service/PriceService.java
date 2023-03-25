package webapp.booking.service;

import java.time.LocalDate;
import java.util.List;
import webapp.booking.dto.ReservationPriceDTO;

public interface PriceService {
    List<ReservationPriceDTO> findAllPrice();
    Boolean insertAllPrice(List<ReservationPriceDTO> dtoList);
    Integer findPriceByDate(LocalDate date);
}
