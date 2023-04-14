package webapp.event.service;


import core.service.BasicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import webapp.event.dto.EventDTO;
import webapp.event.pojo.Event;
import webapp.event.repository.EventRepository;
import webapp.others.pojo.EventNews;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends BasicService<EventRepository, Event, EventDTO>
        implements EventService {

    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;

    public static final String HASH_KEY="EVENT_NEWS";
    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    public EventServiceImpl(ModelMapper modelMapper, EventRepository eventRepository) {
        super(modelMapper, eventRepository);
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean insert(EventDTO eventDTO) {
        Event event = modelMapper.map(eventDTO, Event.class);
        eventRepository.save(event);
        return true;
    }

    @Override
    public List<EventDTO> getAllEvent() {
        return getAllDTO();
    }

    @Override
    public Boolean updateWinner(EventDTO eventDTO) {
        eventRepository.updateEventWinners(eventDTO.getEventWinner1(), eventDTO.getEventWinner2(), eventDTO.getEventWinner3(), eventDTO.getEventStatus(), eventDTO.getEventNo());
        return true;
    }

    @Override
    public Boolean updateSignupNum(EventDTO eventDTO) {
        Integer signupNum = eventRepository.getEventSignupNum(eventDTO.getEventNo());
        Integer eventLimit = eventRepository.getEventLimit(eventDTO.getEventNo());
        if (signupNum == eventLimit) {
            return false;
        } else {
            eventRepository.updateEventSignupNum(1, eventDTO.getEventNo());
            return true;
        }
    }


    @Override
    public Boolean cancelSignupNum(EventDTO eventDTO) {
        Integer signupNum = eventRepository.getEventSignupNum(eventDTO.getEventNo());
        if (signupNum == 0) {
            return false;
        } else {
            eventRepository.updateEventSignupNum(-1, eventDTO.getEventNo());
            return true;
        }
    }

    @Override
    public Integer selectEventLimit(EventDTO eventDTO) {
        return eventRepository.getEventLimit(eventDTO.getEventNo());
    }

    @Override
    public Boolean updateEventStatus(EventDTO eventDTO){
        if(eventDTO.getEventStatus() == 0 || eventDTO.getEventStatus() == 1 || eventDTO.getEventStatus() == 2){
            eventRepository.setEventStatus(eventDTO.getEventStatus(),eventDTO.getEventLimit(), eventDTO.getSignupNum() ,eventDTO.getEventNo());
            return true;
        }else {
            return false;
        }


    }

    @Override
    public List<EventNews> saveDailyNewsToRedis() {
        List<Event> eventList = eventRepository.findEventNewByStatus();
        // Event -> EventNews
        List<EventNews> eventNewsList = eventList.stream()
                .map(event -> modelMapper.map(event, EventNews.class))
                .collect(Collectors.toList());
        System.out.println(eventNewsList.size());
        // 存到Redis
        for (EventNews eventNews : eventNewsList) {
            System.out.println(eventNewsList);
            String key = HASH_KEY+":" + eventNews.getEventNo();
            redisTemplate.opsForValue().set(key, eventNews);
        }
        return eventNewsList;
    }

    @Override
    public EventNews randomSelectOneEvent(){
        List<Object> keys = redisTemplate.opsForValue().multiGet(redisTemplate.keys(HASH_KEY + ":*"));
        if (keys != null && !keys.isEmpty()) {
            int randomIndex = new Random().nextInt(keys.size());
            Object randomKey = keys.get(randomIndex);
            System.out.println(randomKey);
            return (EventNews) randomKey;
        }
        return null;
    }
}





