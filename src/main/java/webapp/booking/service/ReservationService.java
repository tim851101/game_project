package webapp.booking.service;

import core.service.IntrinsicService;
import java.util.List;
<<<<<<< HEAD
import org.springframework.stereotype.Service;
import webapp.booking.dto.OpenHourDTO;

@Service
=======
import webapp.booking.dto.OpenHourDTO;

>>>>>>> 4f56a669f32301d81d89e8be88f0f51552d150ac
public interface ReservationService extends IntrinsicService<OpenHourDTO> {

    List<OpenHourDTO> getAllOpenHour();
    Boolean insertAllOpenHour(List<OpenHourDTO> dtoList);
    void insert(OpenHourDTO dto);
}
