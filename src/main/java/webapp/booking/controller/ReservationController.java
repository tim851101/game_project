package webapp.booking.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webapp.booking.dto.OpenHourDTO;
import webapp.booking.service.ReservationService;

@RestController
@RequestMapping("/rev")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/ls")
    public List<OpenHourDTO> getAllOpenHour() {
        return reservationService.getAllDTO();
    }

    @PostMapping("/save-all")
    @ResponseBody
    public Boolean insertAll(@RequestBody List<OpenHourDTO> dtoList) {
        try {
            reservationService.insertAllDTO(dtoList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
