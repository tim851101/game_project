package webapp.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.event.dto.EventDTO;
import webapp.event.service.EventServiceImpl;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventServiceImpl eventServiceImpl;

    @Autowired
    public EventController(EventServiceImpl eventServiceImpl) {

        this.eventServiceImpl = eventServiceImpl;
    }


    @PostMapping("/save-event")
    @ResponseBody
    public Boolean insertAllEvent(@RequestBody EventDTO eventDTO){
         return eventServiceImpl.insert(eventDTO);

    }
    @GetMapping("/ls-event")
    public List<EventDTO> getAllEvent() {

        return eventServiceImpl.getAllEvent();
    }

    @PostMapping("/updateWinners")
    @ResponseBody
    public Boolean updateWinner(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.updateWinner(eventDTO);
    }
    @PostMapping("/addSignupNum")
    @ResponseBody
    public Boolean updateSignupNum(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.updateSignupNum(eventDTO);
    }
    @PostMapping("/delSignupNum")
    @ResponseBody
    public Boolean cancelSignupNum(@RequestBody EventDTO eventDTO){
        return eventServiceImpl.cancelSignupNum(eventDTO);
    }

}
