package webapp.booking.service;

import core.service.intrinsicService;
import java.util.List;
import org.springframework.stereotype.Service;
import webapp.booking.dto.OpenHourDTO;

@Service
public interface ReservationService extends intrinsicService<OpenHourDTO> {

    List<OpenHourDTO> getAllOpenHour();
    Boolean insertAllOpenHour(List<OpenHourDTO> dtoList);
    void insert(OpenHourDTO dto);
}
