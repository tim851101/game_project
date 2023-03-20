package webapp.member.controller;

import org.springframework.web.bind.annotation.*;
import webapp.member.dto.LoginDTO;
import webapp.member.service.MemberServiceImpl;

@RestController
@RequestMapping
public class LoginController {

    final MemberServiceImpl memberServiceImpl;
    public LoginController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    @PostMapping("/login-check")
    @ResponseBody
    public Boolean memberLogin(@RequestBody LoginDTO loginDTO){
        return memberServiceImpl.memberLogin(loginDTO);
    }


//    @GetMapping("/foreground/login")
//    public String showLoginPage() {
//        return "foreground/login"; //返回視圖名稱
//    }
//    @GetMapping("/")
//    @Qualifier("loginController")
//    public String showLoginPage() {
//        return "login";
//    }


}
