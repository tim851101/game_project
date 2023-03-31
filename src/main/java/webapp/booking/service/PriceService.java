package webapp.booking.service;

import java.time.LocalDate;
import java.util.List;
<<<<<<< HEAD
import org.springframework.stereotype.Service;
import webapp.booking.dto.ReservationPriceDTO;

@Service
=======
import webapp.booking.dto.ReservationPriceDTO;

>>>>>>> 4f56a669f32301d81d89e8be88f0f51552d150ac
public interface PriceService {
    List<ReservationPriceDTO> findAllPrice();
    Boolean insertAllPrice(List<ReservationPriceDTO> dtoList);
    Integer findPriceByDate(LocalDate date);
}
