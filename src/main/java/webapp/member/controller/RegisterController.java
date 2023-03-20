package webapp.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.RegisterDTO;
import webapp.member.service.MemberServiceImpl;


@Controller
@RequestMapping("/mem")
public class RegisterController {


    final MemberServiceImpl memberServiceImpl;
    public RegisterController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    @PostMapping("/reg")
    @ResponseBody
    public Boolean addMember(@RequestBody RegisterDTO registerDTO){
        return memberServiceImpl.addMember(registerDTO);
    }


    @PostMapping("/login-check")
    @ResponseBody
    public Boolean memberLogin(@RequestBody LoginDTO loginDTO){
        return memberServiceImpl.memberLogin(loginDTO);
    }


//    @GetMapping("/foreground/register")
//    public String showRegisterPage() {
//        return "forward:/foreground/register.html"; //返回視圖名稱
//    }

//    @GetMapping("/")
////    @Qualifier("registerController")
//    public String showRegisterPage() {
//        return "/foreground/register";
//    }

}
