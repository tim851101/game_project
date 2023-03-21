package webapp.booking.service;

import core.service.IntrinsicService;
import java.util.List;
import org.springframework.stereotype.Service;
import webapp.booking.dto.OpenHourDTO;

@Service
public interface ReservationService extends IntrinsicService<OpenHourDTO> {

    List<OpenHourDTO> getAllOpenHour();
    Boolean insertAllOpenHour(List<OpenHourDTO> dtoList);
    void insert(OpenHourDTO dto);
}
