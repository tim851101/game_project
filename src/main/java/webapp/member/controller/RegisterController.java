package webapp.member.controller;

import com.nimbusds.jose.shaded.gson.Gson;
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
    public Object addMember(@RequestBody MemberDTO user) {
        try {
            return memberServiceImpl.addMember(user);
        }catch (Exception e) {
            e.printStackTrace();
            Gson gson = new Gson();
            String errorMsg = gson.toJson(e.getMessage());
            System.out.println(errorMsg);
            return errorMsg;
        }

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
