package webapp.booking.service;

import core.BasicService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.booking.dto.OpenHourDTO;
import webapp.booking.pojo.OpenHour;
import webapp.booking.repository.OpenHourRepository;

@Service
public class ReservationService extends BasicService<OpenHourRepository, OpenHour, OpenHourDTO> {

    public ReservationService(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
