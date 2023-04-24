package webapp.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webapp.employee.service.VerifyService;

@RestController
@RequestMapping("/emp")
public class EmployeeMailController {

    final private VerifyService verifyService;

    @Autowired
    public EmployeeMailController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @GetMapping("/mail")
    public Boolean sendSimpleMail(@RequestParam String email) {
        return verifyService.sendAndStoreVerify(email);
    }

    @GetMapping("/find-code")
    public Integer findVerifyCode(@RequestParam String email){
        return verifyService.findVerifyCode(email);
    }
}
