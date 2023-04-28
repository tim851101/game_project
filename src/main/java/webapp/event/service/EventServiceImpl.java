package webapp.event.service;

import core.service.BasicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import webapp.event.dto.EventDTO;
import webapp.event.pojo.Event;
import webapp.event.repository.EventRepository;
import webapp.others.pojo.EventNews;
import webapp.others.pojo.News;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends BasicService<EventRepository, Event, EventDTO>
        implements EventService {

    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;

    public static final String HASH_KEY = "EVENT_NEWS";
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
        eventRepository.updateEventWinners(eventDTO.getEventWinner1(), eventDTO.getEventWinner2(),
                eventDTO.getEventWinner3(), eventDTO.getEventStatus(), eventDTO.getEventNo());
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
    public Boolean updateEventStatus(EventDTO eventDTO) {
        eventRepository.setEventStatus(eventDTO.getEventStatus(), eventDTO.getEventNo());
        return true;

    }



    @Override
    public EventNews randomSelectOneEvent() {
        List<Object> keys = redisTemplate.opsForValue().multiGet(redisTemplate.keys(HASH_KEY + ":*"));
        if (keys != null && !keys.isEmpty()) {
            int randomIndex = new Random().nextInt(keys.size());
            Object randomKey = keys.get(randomIndex);
            System.out.println(randomKey);
            return (EventNews) randomKey;
        }
        return null;
    }
    // 排程報名賽事資訊至前端，每日7-10、20-23點執行一次。
//    @Scheduled(cron = "0 0 7-10,20-23 * * *")
//    public void checkEventStatus() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh");
//        String localDate = dateFormat.format(new Date());
//        List<Event> events = eventRepository.findAll();
//        for (Event event : events) {
//            String start = dateFormat.format(event.getSignupStartTime());
//            String end = dateFormat.format(event.getSignupDeadline());
//            if (event.getEventStatus() == null && localDate.equals(start)) {
//                eventRepository.setEventStatus((byte) 0, event.getEventNo());
//                System.out.println("賽事報名開始");
//            }
//            if (event.getEventStatus() == 0 && localDate.equals(end)) {
//                eventRepository.setEventStatus((byte) 3, event.getEventNo());
//                System.out.println("賽事報名截止");
//            }
//
//        }
//    }



    public EventDTO selectOneEvent(Integer eventNo) {
       Event event= eventRepository.getReferenceById(eventNo);
      return  modelMapper.map(event,EventDTO.class);
    }
}
