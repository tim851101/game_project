package webapp.event.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import webapp.event.dto.EventOrdDTO;
import webapp.event.service.EventOrdservice;


@RestController
@RequestMapping("/eventord")
@CrossOrigin(origins = "*")
public class EventOrdcontroller {

    @Autowired
    private EventOrdservice EventOrdservice;

    @GetMapping("/eve")
    public String test(){
        return "aaa";
    }

    @GetMapping("/ls")
    public List<EventOrdDTO> getAlleventOrd(){
        return EventOrdservice.getAllEventOrd();
    }
    @GetMapping("byeventls")
    public List<EventOrdDTO> getAllmembyevent(Integer Eventno){
        return EventOrdservice.getAllmembyevent(Eventno);
    }


}
