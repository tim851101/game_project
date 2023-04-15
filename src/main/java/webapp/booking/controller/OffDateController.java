package webapp.booking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapp.booking.dto.OffDateReqDTO;
import webapp.booking.dto.OffDateResDTO;
import webapp.booking.service.OffDateService;

@RestController
@RequestMapping("/off-date")
public class OffDateController {
    private final OffDateService offDateService;

    @Autowired
    public OffDateController(OffDateService offDateService) {
        this.offDateService = offDateService;
    }

    @PostMapping("/save-all")
    public Boolean save(@RequestBody List<OffDateResDTO> dtoList) {
        return offDateService.saveAll(dtoList);
    }

    @PostMapping("/find-all")
    public List<OffDateResDTO> findAll(@RequestBody List<OffDateReqDTO> idList) {
        return offDateService.findAll(idList);
    }
}
