package webapp.booking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webapp.booking.dto.OpenHourDTO;
import webapp.booking.pojo.OpenHour;
import webapp.booking.service.ReservationService;

@RestController
@RequestMapping("/rev")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/ls")
    public List<OpenHourDTO> getAllOpenHour() {
        return reservationService.getAllDTO();
    }

    @GetMapping("/insert-test")
//    public void insert(@RequestParam OpenHourDTO dto){
    public void insert(@RequestParam(name = "week") String week) {
//        reservationService.insert(dto);
    }

    @GetMapping("/insert")
    public void insert(@RequestParam OpenHourDTO dto) {
        reservationService.insert(dto);
    }

    @PostMapping("/insert-all")
    public void insertAll(@RequestBody List<OpenHourDTO> dtoList) {
        reservationService.insertAll(dtoList);
    }

    @GetMapping("/week")
    public OpenHourDTO selectByWeek(@RequestParam Integer week){
        return reservationService.selectByWeek(week);
    }

    @PostMapping("/updateByWeek")
    public void updateByWeek(@RequestBody OpenHourDTO dto){
        reservationService.updateByWeek(dto);
    }
}
