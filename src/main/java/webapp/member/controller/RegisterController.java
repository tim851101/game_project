package webapp.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        // 驗證user輸入資料

        // 密碼加密
        return memberServiceImpl.addMember(registerDTO);
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
