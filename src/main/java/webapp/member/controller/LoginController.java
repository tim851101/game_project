package webapp.member.controller;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import webapp.member.dto.LoginDTO;
import webapp.member.service.MemberServiceImpl;

import java.util.Map;

@RestController
@RequestMapping
public class LoginController {

    final MemberServiceImpl memberServiceImpl;
    public LoginController(MemberServiceImpl memberServiceImpl) {
        this.memberServiceImpl = memberServiceImpl;
    }

    // bug
    @PostMapping(path = "/mem/login",consumes = "application/json")
    public String memberLogin(@RequestBody LoginDTO loginDTO){
        System.out.println(memberServiceImpl.memberLogin(loginDTO));
        if (memberServiceImpl.memberLogin(loginDTO)){
            String url ="https://localhost:8082/foreground/my-account.html";
            return "redirect:"+url;
        }else {
//            Map<String, Object> map = null;
//            map.put("loginMsg","帳號密碼錯誤");
            String url ="https://localhost:8082/foreground/login.html";
            return "redirect:"+url;
        }
    }
    @PostMapping(path = "/mem/login",consumes = "application/x-www-form-urlencoded")
    public String memberLogin1(LoginDTO loginDTO){
        if (memberServiceImpl.memberLogin(loginDTO)){
            String url ="https://localhost:8082/foreground/my-account.html";
            return "redirect:"+url;
        }else {
//            Map<String, Object> map = null;
//            map.put("loginMsg","帳號密碼錯誤");
            String url ="https://localhost:8082/foreground/login.html";
            return "redirect:"+url;
        }
    }

//    @PostMapping(path = "/test", consumes = "application/json")
//    public String test(@RequestBody User user) {
//        return user.toString();
//    }
//
//    @PostMapping(path = "/test", consumes = "application/x-www-form-urlencoded")
//    public String test(User user) {
//        return user.toString();
//    }


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
