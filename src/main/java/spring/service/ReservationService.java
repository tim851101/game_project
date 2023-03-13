package spring.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dto.OpenHourDTO;
import spring.model.OpenHour;
import spring.repository.OpenHourRepository;

@Service
public class ReservationService {
    @Autowired // DI
    private OpenHourRepository openHourRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<OpenHourDTO> getAllOpenHour(){
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        return openHourRepository.findAll()
            .stream()
            .map(this::EntityToDTO)
            .collect(Collectors.toList());
    }

    private OpenHourDTO EntityToDTO(OpenHour openHour) {
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);
        OpenHourDTO openHourDTO = new OpenHourDTO();
        openHourDTO = modelMapper.map(openHour, OpenHourDTO.class);
        return openHourDTO;
    }
}
