package webapp.member.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import webapp.member.dto.ChangePwdDTO;
import webapp.member.dto.MemberDTO;
import webapp.member.pojo.ErrorResponse;
import webapp.member.pojo.Members;
import webapp.member.repository.MemberRepository;
import webapp.member.service.MemberService;
import webapp.member.service.MemberVaildationRules;
import webapp.others.service.EmailService;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;
import java.util.stream.Collectors;

import static webapp.member.service.MemberServiceImpl.HASH_KEY;

@RestController
@CrossOrigin
@RequestMapping("/mem")
public class MemberController {

    @Autowired
    private MemberService memberServiceImpl;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    @Autowired
    private EmailService emailServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;


    // @GetMapping("/login")
    // public RedirectView redirectToLogin(HttpServletRequest request) {
    // String currentUrl = request.getRequestURL().toString();
    // String redirectUrl = currentUrl.replace("/mem/login",
    // "/foreground/login.html");
    // return new RedirectView(redirectUrl);
    // }

//    @PostMapping("location")
//    public String checkLocation() {
//        StringBuffer url = request.getRequestURL();
//        String uri = request.getRequestURI();
//        String baseUrl = url.substring(0, url.length() - uri.length()) + request.getContextPath() + "/";
//        System.out.println("當前網址URL: " + url.toString() + "\n當前路徑URI: " + uri + "\n基本網址URL: " + baseUrl);
//        return "redirect:";
//    }

    // 取得當前路徑網址方法,但html似乎無法這樣使用
    public void saveReturnUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        StringBuilder baseUrlBuilder = new StringBuilder(scheme);
        baseUrlBuilder.append("://").append(serverName);
        if (serverPort != 80 && serverPort != 443) {
            baseUrlBuilder.append(":").append(serverPort);
        }
        baseUrlBuilder.append(contextPath);
        String baseUrl = baseUrlBuilder.toString();

        String currentUrl = request.getRequestURI();
        String queryString = request.getQueryString();

        StringBuilder locationBuilder = new StringBuilder(baseUrl);
        locationBuilder.append(currentUrl);
        if (queryString != null) {
            locationBuilder.append("?").append(queryString);
        }
        String location = locationBuilder.toString();

        HttpSession session = request.getSession();
        session.setAttribute("location", location);
    }

    // 取得client cookie的sessionId
    // 比對Redis找出client的memNo
    // 有回傳客戶編號,沒有回傳null
    // 會員編號放在header的<div hidden id="memNo_header">{{ memNo }}</
    // 應該結合showLoginForm,當回傳值是null時->儲存當前頁面->導到登入頁面->前端js導回先前頁面
    @PostMapping("/get-memNo")
    public Integer getMemNoForRedisUseSession(HttpServletRequest request){
        // 檢查 session 和 request 是否為 null
        if (request == null) {
            return null;
        }
        // 由header獲取cookie
        String cookieHeader = request.getHeader("Cookie");
        if (cookieHeader == null) {
            return null; // 如果Cookie不存在，返回-1
        }
        // 解析Cookie字符串，将每个Cookie键值對解析為一個Cookie
        List<HttpCookie> cookies = HttpCookie.parse(cookieHeader);
        System.out.println("cookies.size()"+cookies.size()); // test
        System.out.println(cookies); // test
        String[] cookiePairs = cookieHeader.split("; ");
        String jsessionId = null;
        for (String cookiePair : cookiePairs) {
            String[] keyValue = cookiePair.split("=");
            if (keyValue[0].equals("JSESSIONID")) {
                jsessionId = keyValue[1];
                break;
            }
        }

        if (jsessionId == null) {
            return null; // 如果JSESSIONID Cookie不存在，返回null
        }

        System.out.println("jsessionId : "+jsessionId); // test
        String hashKey=HASH_KEY+":"+jsessionId;

        // 使用JSESSIONID找Redis中的客戶登入時的sessionId進而取得會員號碼
        String memNoStr = (String) redisTemplate.opsForValue().get(hashKey);
        String[] arr = memNoStr.split("=");
        String numberStr = arr[1].substring(0, arr[1].length() - 1); // 拿到等號右邊
        Integer memNo = Integer.parseInt(numberStr);

        System.out.println("memNo : "+memNo);
        System.out.println("find one member is "+ memNo); // test
        if (memNo == null) {
            return null; // 用戶不存在，返回-1
        }
        // 用戶存在返回會員編號
        return memNo;
    }

    // 由別的網址到login.html頁面紀錄前次頁面
//    @GetMapping("/to-location")
//    public String checkLocation() {
//        StringBuffer url = request.getRequestURL();
//        String uri = request.getRequestURI();
//        String baseUrl = url.substring(0, url.length() - uri.length()) + request.getContextPath() + "/";
//        System.out.println("當前網址URL: " + url.toString() + "\n當前路徑URI: " + uri + "\n基本網址URL: " + baseUrl);
//        return "redirect:";
//    }


    // 需要會員功能相關頁面,如購物車.活動報名.預定座位
    @GetMapping("/to-login-page")
    public String showLoginForm(HttpServletRequest request, Model model) {
        saveReturnUrl(request); // 儲存當前頁面的路徑
        StringBuilder redirectUrl = new StringBuilder(request.getContextPath())
                .append("/foreground/login.html");
        return "redirect:" + redirectUrl;
    }


    // toLogin
    // 1.取得當前路徑網址儲存在session => location(key):currentUrl(value)
    // 2.登入成功後儲存sessionId與memNo到redis
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ErrorResponse> login(
            @Validated(MemberVaildationRules.MemLogin.class) @RequestBody MemberDTO login,
//            HttpServletRequest request,
//            HttpServletResponse response,
            BindingResult bindingResult
    ) throws IOException {
        // 檢查輸入資料格式正確性
        // 回傳型態
        // {
        //    "status": "BAD_REQUEST",
        //    "errors": [
        //        "Email格式不正確",
        //        "Email不可為空白"
        //    ]
        // }
        System.out.println(login.getMemEmail());
        System.out.println("incoming" + login);
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            System.out.println(errors);
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors));
        }
        // 檢查會員存不存在或帳密輸入是否錯誤
        // 回傳型態
        // {
        //    "status": "BAD_REQUEST",
        //    "errors": [
        //        "帳號或密碼輸入錯誤"
        //    ]
        //}
//        System.out.println(loginDTO.getMemEmail()+ loginDTO.getMemPassword());
        Members memberOptional = memberServiceImpl.toLogin(login.getMemEmail(), login.getMemPassword());
        if (memberOptional != null) {
            // 當會員存在執行以下
            Members member = memberOptional;
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            System.out.println(member.getMemNo());
            session.setAttribute("memNo", member.getMemNo()); // 将会员编号存储到 session 中
            System.out.println(session.getAttribute("memNo"));
            // 将sessionId存储到localStorage中
            String script = "localStorage.setItem('sessionId', '" + sessionId + "');";
            System.out.println(script);

            // 将sessionId和會員資料存到Redis
            memberServiceImpl.saveSessionToRedis(sessionId, member);
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(HttpStatus.OK, "Login successful", Collections.emptyList()));
        } else {
            errors = Collections.singletonList("帳號或密碼輸入錯誤");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Login failed", errors));
        }
    }



    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<ErrorResponse> createUser(
            @Validated(MemberVaildationRules.MemAdd.class) @RequestBody MemberDTO memberDTO,
            BindingResult bindingResult)
    {
        System.out.println(memberDTO.toString());
        List<String> errors = new ArrayList<>();
        // 檢查輸入資料格式正確性
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation failed" ,errors));
        }
        // 檢查信箱是否被註冊,已被註冊
        if (memberRepository.existsByMemEmail(memberDTO.getMemEmail())) {
            errors = Collections.singletonList("此信箱已被註冊，請勿重複註冊");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Register failed",errors));
        }
        // 有任何的錯誤訊息存在，就會直接回傳 bad request
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation failed", errors));
        }
        try {
            System.out.println("ready to add...");
            memberServiceImpl.addMember(memberDTO);
        }catch (Exception e) {
            System.out.println("something error...");
            e.printStackTrace();
        }
        System.out.println("ready to end...");
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(HttpStatus.OK, "Register successful", Collections.emptyList()));
    }

    // 會員更新資料使用
    @PostMapping("/save")
//    @ResponseBody
    public ResponseEntity<ErrorResponse> updateUser(
            @Validated(MemberVaildationRules.MemUpdate.class) @RequestBody MemberDTO memberDTO,
            HttpServletRequest request,
            BindingResult bindingResult)
    {
        HttpSession session = request.getSession();
        Integer memNo= (Integer) session.getAttribute("memNo");

        List<String> errors = new ArrayList<>();
        // 檢查輸入資料格式正確性
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation failed", errors));
        }
        // 檢查更新的信箱是否被註冊,已被註冊
        // 更新的Email
        String memEmail = memberDTO.getMemEmail();
        MemberDTO member=memberServiceImpl.findById(memberDTO.getMemNo());
        // 舊Email不等於新Email且現有資料庫存在新Email
        if (!member.getMemEmail().equals(memEmail) && memberRepository.existsByMemEmail(memEmail)) {
            errors = Collections.singletonList("此信箱已被註冊，請勿重複註冊");
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation failed", errors));
        }
        // 有任何的錯誤訊息存在，就會直接回傳 bad request
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation failed", errors));
        }
        try {
            System.out.println("start...78");
            System.out.println(memberDTO.toString());
            memberServiceImpl.updateMember(memberDTO);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

    // 會員變更密碼使用
    @PostMapping("/changePassword")
//    @ResponseBody
    public ResponseEntity<?> changePwd(@Validated(MemberVaildationRules.MemChangePassword.class) @RequestBody ChangePwdDTO changePwdDTO,
                                                   HttpServletRequest request,
                                                   BindingResult bindingResult){
        HttpSession session = request.getSession();
        Integer memNo= (Integer) session.getAttribute("memNo");
        memNo=changePwdDTO.getMemNo();
        // 檢查輸入資料格式正確性
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed",errors));
        }
        MemberDTO member = memberServiceImpl.findById(memNo);
        // 檢查舊密碼是否正確
        if (!encoder.matches(changePwdDTO.getOldPwd(), member.getMemPassword())) {
            errors = new ArrayList<>();
            errors.add("舊密碼不正確");
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation failed", errors));
        }
        member.setMemPassword(encoder.encode(changePwdDTO.getNewPwd()));
        memberServiceImpl.updatePwd(member);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/save")
//    @ResponseBody
//    public String saveMember(@RequestBody MemberDTO memberDTO) {
//        try {
//            return memberServiceImpl.saveMember(memberDTO);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Gson gson = new Gson();
//            String errorMsg = gson.toJson(e.getMessage());
//            System.out.println(errorMsg);
//            return errorMsg;
//        }
//    }
//    @PostMapping("/login")
//    @ResponseBody
//    public String login(@RequestBody LoginDTO loginDTO) {
//        System.out.println(loginDTO);
//        if (memberServiceImpl.toLogin(loginDTO.getMemEmail(),loginDTO.getMemPassword()).isPresent()) {
//            // 登入成功，將 email 存儲在 session 中
//            request.getSession().setAttribute("email", loginDTO.getMemEmail());
//            String email = (String) request.getSession().getAttribute("email");
//            System.out.println(memberServiceImpl.findByMemEmail(email));
//            JSONObject object = new JSONObject();
//            object.put("memNo", memberServiceImpl.findByMemEmail(email).getMemNo());
//            object.put("memName", memberServiceImpl.findByMemEmail(email).getMemName());
//            return object.toString();
//        } else {
//            // 登入失敗，將錯誤訊息傳遞到前端頁面
//            return "帳號或密碼錯誤";
//        }
//    }

    // 由會員編號找到會員資訊
    @GetMapping("/find-one")
    @ResponseBody
    public MemberDTO findMemById(@RequestParam Integer id,HttpServletRequest request) {
        HttpSession session=request.getSession();
        System.out.println(session);
        return memberServiceImpl.findById(id);
    }

    @GetMapping("/ls-one")
    @ResponseBody
    public MemberDTO findById(@RequestParam Integer id,HttpServletRequest request) {
        HttpSession session=request.getSession();
        Integer memNo= (Integer) session.getAttribute("memNo");
        System.out.println(session);
        System.out.println(memNo);
        return memberServiceImpl.findById(id);
    }



    @GetMapping("/find-email")
    @ResponseBody
    public MemberDTO findByMemEmail(@RequestParam String email) {
        System.out.println("start");
        System.out.println(memberServiceImpl.findByMemEmail(email));
        return memberServiceImpl.findByMemEmail(email);
    }



    // 當會員忘記密碼時,填寫信箱用來取的驗證碼登入
    @PostMapping("/genAuthCode")
    @ResponseBody
    public String forgetPassword(@RequestBody String memEmail) throws MessagingException {
        System.out.println(memEmail);
        return memberServiceImpl.getNewPassword(memEmail);
    }

}
