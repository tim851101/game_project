package webapp.others.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import webapp.event.pojo.Event;
import webapp.event.repository.EventRepository;
import webapp.event.service.EventService;
import webapp.others.pojo.EventNews;

import java.util.List;
import java.util.stream.Collectors;

import static webapp.event.service.EventServiceImpl.HASH_KEY;

@Component
public class EventNewsSchedule {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 50 23 * * ?") // "0 59 23 * * ?"
    public void saveDailyNewsToRedis() {
        List<Event> eventList = eventRepository.findEventNewByStatus();
        // Event -> EventNews
        List<EventNews> eventNewsList = eventList.stream()
                .map(event -> modelMapper.map(event, EventNews.class))
                .collect(Collectors.toList());
        System.out.println(eventNewsList.size());
        // 存到Redis
        for (EventNews eventNews : eventNewsList) {
            System.out.println(eventNewsList);
            String key = HASH_KEY + ":" + eventNews.getEventNo();
            redisTemplate.opsForValue().set(key, eventNews);
        }
    }

}
