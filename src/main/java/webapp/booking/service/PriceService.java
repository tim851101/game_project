package webapp.booking.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import webapp.booking.dto.ReservationPriceDTO;

@Service
public interface PriceService {
    List<ReservationPriceDTO> findAllPrice();
    Boolean insertAllPrice(List<ReservationPriceDTO> dtoList);
    Integer findPriceByDate(LocalDate date);
}
