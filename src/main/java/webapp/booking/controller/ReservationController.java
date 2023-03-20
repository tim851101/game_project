package webapp.booking.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webapp.booking.dto.OpenHourDTO;
import webapp.booking.dto.ReservationPriceDTO;
import webapp.booking.service.PriceService;
import webapp.booking.service.ReservationService;

@RestController
@RequestMapping("/rev")
public class ReservationController {
    /**
     * All input parameters send from frontend must strictly match
     * 1. DTO variables type
     * 2. DTO variables name
     * NOTE: if input parameters type is List<DTO> frontend request json
     * must like [{...}, {...}, ...]
     */
    private final ReservationService reservationService;
    private final PriceService priceService;

    public ReservationController(ReservationService reservationService, PriceService priceService) {
        this.reservationService = reservationService;
        this.priceService = priceService;
    }

    @GetMapping("/ls")
    public List<OpenHourDTO> getAllOpenHour() {
        return reservationService.getAllDTO();
    }

    @PostMapping("/save-all")
    @ResponseBody
    public Boolean insertAll(@RequestBody List<OpenHourDTO> dtoList) {
        return reservationService.insertAll(dtoList);
    }

    @PostMapping("/save-all-price")
    @ResponseBody
    public Boolean insertAllPrice(@RequestBody List<ReservationPriceDTO> dtoList) {
        return priceService.insertAllPrice(dtoList);
    }

    @GetMapping("/ls-price")
    @ResponseBody
    public List<ReservationPriceDTO> findAllPrice() {
        return priceService.findAllPrice();
    }

    @GetMapping("/price-by-date")
    @ResponseBody
    public Integer findPriceByDate(LocalDate date) {
        return priceService.findPriceByDate(date);
    }
}
