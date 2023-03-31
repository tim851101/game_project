package webapp.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.member.dto.MemberDTO;
import webapp.member.service.MemberService;


@Controller
@RequestMapping("/mem")
public class RegisterController {

    @Autowired
    private MemberService memberServiceImpl;


    @PostMapping("/reg")
    @ResponseBody
    public Boolean addMember(@RequestBody MemberDTO memberDTO){
        // 驗證user輸入資料

        // 密碼加密
        return memberServiceImpl.addMember(memberDTO);
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
