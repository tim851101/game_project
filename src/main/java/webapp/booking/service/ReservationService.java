package webapp.booking.service;

import core.service.BasicService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import webapp.booking.dto.OpenHourDTO;
import webapp.booking.pojo.OpenHour;
import webapp.booking.repository.OpenHourRepository;

@Service
public class ReservationService extends BasicService<OpenHourRepository, OpenHour, OpenHourDTO> {
    // TODO: sanity checker
    // Spring recommend constructor injection
    private final OpenHourRepository openHourRepository;
    private final ModelMapper modelMapper;
    public ReservationService(ModelMapper modelMapper, OpenHourRepository openHourRepository) {
        super(modelMapper, openHourRepository);
        this.modelMapper = modelMapper;
        this.openHourRepository = openHourRepository;
    }


    public void insert(OpenHourDTO dto) {
        openHourRepository.save(modelMapper.map(dto, OpenHour.class));
    }

    public Boolean insertAll(List<OpenHourDTO> dtoList){
        try {
            insertAllDTO(dtoList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
