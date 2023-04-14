package webapp.others.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import webapp.event.repository.EventRepository;

@Component
public class EventNewsScheduler implements ApplicationRunner {
    @Autowired
    private EventRepository eventRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        eventRepository.createDailyNewsQueryEvent();
    }
}
