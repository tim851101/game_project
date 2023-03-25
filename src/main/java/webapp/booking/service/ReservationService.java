package webapp.booking.service;

import core.service.IntrinsicService;
import java.util.List;
import webapp.booking.dto.OpenHourDTO;

public interface ReservationService extends IntrinsicService<OpenHourDTO> {

    List<OpenHourDTO> getAllOpenHour();
    Boolean insertAllOpenHour(List<OpenHourDTO> dtoList);
    void insert(OpenHourDTO dto);
}
