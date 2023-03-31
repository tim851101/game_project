package webapp.booking.service;

import core.service.BasicService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Component;
=======
import org.springframework.stereotype.Service;
>>>>>>> 4f56a669f32301d81d89e8be88f0f51552d150ac
import webapp.booking.dto.OpenHourDTO;
import webapp.booking.pojo.OpenHour;
import webapp.booking.repository.OpenHourRepository;

<<<<<<< HEAD
@Component
=======
@Service
>>>>>>> 4f56a669f32301d81d89e8be88f0f51552d150ac
public class ReservationServiceImpl extends BasicService<OpenHourRepository, OpenHour, OpenHourDTO>
    implements ReservationService {
    // TODO: sanity checker
    // Spring recommend constructor injection
    private final OpenHourRepository openHourRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservationServiceImpl(ModelMapper modelMapper, OpenHourRepository openHourRepository) {
        super(modelMapper, openHourRepository);
        this.modelMapper = modelMapper;
        this.openHourRepository = openHourRepository;
    }

    public List<OpenHourDTO> getAllOpenHour() {
        return getAllDTO();
    }

    public void insert(OpenHourDTO dto) {
        openHourRepository.save(modelMapper.map(dto, OpenHour.class));
    }

    public Boolean insertAllOpenHour(List<OpenHourDTO> dtoList) {
        try {
            insertAllDTO(dtoList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
