package webapp.event.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.event.dto.EventOrdDTO;
import webapp.event.pojo.EventOrdVO;
import webapp.event.repository.EventOrdRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventOrdservice {
    @Autowired // DI
    private EventOrdRepository EventOrdRepository;
    @Autowired
    private ModelMapper modelMapper;
    private EventOrdDTO EntityToDTO(EventOrdVO openEventOrd) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        EventOrdDTO eventOrdDTO = new EventOrdDTO();
        eventOrdDTO = modelMapper.map(openEventOrd, EventOrdDTO.class);
        return eventOrdDTO;
    }
    public List<EventOrdDTO> getAllEventOrd(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return EventOrdRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
}
