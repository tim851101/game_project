package webapp.member.controller;

import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.MemberDTO;
import webapp.member.service.MemberService;


@Controller
@RequestMapping("/mem")
public class MemberController {

    @Autowired
    private MemberService memberServiceImpl;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

//    @GetMapping("/login")
//    public RedirectView redirectToLogin(HttpServletRequest request) {
//        String currentUrl = request.getRequestURL().toString();
//        String redirectUrl = currentUrl.replace("/mem/login", "/foreground/login.html");
//        return new RedirectView(redirectUrl);
//    }

//    @GetMapping("/login")
//    public RedirectView redirectToLogin() {
//        String redirectUrl = (String) session.getAttribute("currentUrl");
//        return new RedirectView(redirectUrl);
//    }
//    @GetMapping("/login")
//    public String showLogin() {
//        return "/foreground/login";
//    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginDTO loginDTO)  {
        System.out.println(loginDTO);
        if ("登入成功".equals(memberServiceImpl.memberLogin(loginDTO))) {
            // 登入成功，將 email 存儲在 session 中
            session.setAttribute("email", loginDTO.getMemEmail());
            String email= (String) session.getAttribute("email");
            System.out.println(memberServiceImpl.findByMemEmail(email));
            JSONObject object = new JSONObject();
            object.put("memNo",memberServiceImpl.findByMemEmail(email).getMemNo());
            object.put("memName",memberServiceImpl.findByMemEmail(email).getMemName());
            return object.toString();
        } else {
            // 登入失敗，將錯誤訊息傳遞到前端頁面
            return "帳號或密碼錯誤";
        }
    }


    @GetMapping("/find-one")
    @ResponseBody
    public MemberDTO findMemById(@RequestParam Integer id){

        return memberServiceImpl.findById(id);
    }
    @GetMapping("/ls-one")
    @ResponseBody
    public MemberDTO findById(@RequestParam Integer id){
        return memberServiceImpl.findById(id);
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveMember(@RequestBody MemberDTO memberDTO){
        try {
            return memberServiceImpl.saveMember(memberDTO);
        }catch (Exception e) {
            e.printStackTrace();
            Gson gson = new Gson();
            String errorMsg = gson.toJson(e.getMessage());
            System.out.println(errorMsg);
            return errorMsg;
        }
    }


    @GetMapping("/find-email")
    @ResponseBody
    public MemberDTO findByMemEmail(@RequestParam String email){
        System.out.println("start");
        System.out.println(memberServiceImpl.findByMemEmail(email));
        return memberServiceImpl.findByMemEmail(email);
    }

    @PostMapping("location")
    public String checkLocation(){
        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();
        String baseUrl = url.substring(0, url.length() - uri.length()) + request.getContextPath() + "/";
        System.out.println("當前網址URL: " + url.toString() + "\n當前路徑URI: " + uri + "\n基本網址URL: " + baseUrl);
        return "redirect:";
    }



}
