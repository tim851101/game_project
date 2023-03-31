package webapp.event.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import webapp.event.dto.EventOrdDTO;
import webapp.event.service.EventOrdservice;
import webapp.newbooking.dto.BookingDTO;
import webapp.newbooking.pojo.newBooking;
import webapp.newbooking.service.BookingService;

@RestController
@RequestMapping("/  ")
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



}
