package webapp.event.service;


import core.service.BasicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.event.dto.EventDTO;
import webapp.event.pojo.Event;
import webapp.event.repository.EventRepository;

import java.util.List;

@Service
public class EventServiceImpl extends BasicService<EventRepository, Event, EventDTO>
        implements EventService {

    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(ModelMapper modelMapper, EventRepository eventRepository) {
        super(modelMapper, eventRepository);
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    public Boolean insert(EventDTO eventDTO){
        Event event = modelMapper.map(eventDTO, Event.class);
        eventRepository.save(event);
        return true;
    }
    public List<EventDTO> getAllEvent(){
        return getAllDTO();
    }
}
