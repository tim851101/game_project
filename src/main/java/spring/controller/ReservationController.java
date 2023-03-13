package spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.dto.OpenHourDTO;
import spring.service.ReservationService;

@RestController
@RequestMapping("/rev")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/test")
    public String test(){
        return "aaa";
    }

    @GetMapping("/ls")
    public List<OpenHourDTO> getAllOpenHour(){
        return reservationService.getAllOpenHour();
    }
}
