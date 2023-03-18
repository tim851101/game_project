package webapp.booking.service;

import core.BasicService;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
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

    // Spring recommend constructor injection
    public ReservationService(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Autowired
    OpenHourRepository openHourRepository;

    @Autowired
    ModelMapper modelMapper;


    public void updateByWeek(OpenHourDTO dto) {
        System.out.println("into service");
        OpenHour openHour = openHourRepository.findByWeek(dto.getWeek());
        openHour.setOpenTimeStart(dto.getOpenTimeStart());
        openHour.setOpenTimeEnd(dto.getOpenTimeEnd());
        openHourRepository.save(openHour);
    }

    public OpenHourDTO selectByWeek(Integer week) {
        return modelMapper.map(openHourRepository
            .findByWeek(week), OpenHourDTO.class);
    }

    public void insert(OpenHourDTO dto) {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        openHourRepository.save(modelMapper.map(dto, OpenHour.class));
    }

    public void insertAll(List<OpenHourDTO> dto) {
        List<OpenHour> insertList = dto
            .stream()
            .map(this::dtoToEntity)
            .collect(Collectors.toList());
        openHourRepository.saveAll(insertList);
    }

    private OpenHour dtoToEntity(OpenHourDTO pojo) {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(pojo, OpenHour.class);
    }
}
