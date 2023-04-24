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
import java.security.Principal;
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


    // test-google-login
//    @GetMapping("/google-login")
//    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
//        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
//    }

//    @GetMapping("/google-test")
//    public Principal member(Principal principal){
//        System.out.println("username : "+principal.getName());
//        return principal;
//    }
    /*
    * 取得client cookie的sessionId
    * 比對Redis找出client的memNo
    * 有回傳客戶編號,沒有回傳null
    * 可以考慮將會員編號放在header的<div hidden id="memNo_header">{{ memNo }}</>
    * 應該結合showLoginForm,當回傳值是null時->儲存當前頁面->導到登入頁面->前端js導回先前頁面
    */
    @PostMapping("/get-memNo")
    public Integer getMemNoForRedisUseSession(HttpServletRequest request){
        // 檢查 session 和 request 是否為 null
        if (request == null) {
            return null;
        }
        String jsessionId=memberServiceImpl.getJsessionIdFromCookie(request);
        System.out.println("jsessionId : "+jsessionId); // test
        Integer memNo=memberServiceImpl.getMemberNoFromSession(jsessionId);
        if (memNo == null) {
            return null;
        }else {
            return memNo;
        }
    }

    @PostMapping("/to-logout")
    public String toLogout(HttpServletRequest request){
        // 檢查 session 和 request 是否為 null
        if (request == null) {
            return null;
        }
        // 取得client端session id
        String jsessionId=memberServiceImpl.getJsessionIdFromCookie(request);
        if (jsessionId == null) {
            return null;
        }
        System.out.println("jsessionId : "+jsessionId); // test
        String hashKey=HASH_KEY+":"+jsessionId;
        System.out.println("this is delete session id function start....");

        memberServiceImpl.deleteSessionFromRedis(jsessionId);
        return "登出成功";
    }


    /*
    * toLogin
    * 1.取得當前路徑網址儲存在session => location(key):currentUrl(value)
    * 2.登入成功後儲存sessionId與memNo到redis
    * */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ErrorResponse> login(
            @Validated(MemberVaildationRules.MemLogin.class) @RequestBody MemberDTO login,
            HttpServletRequest request,
            BindingResult bindingResult
    ) throws IOException {
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

        Members memberOptional = memberServiceImpl.toLogin(login.getMemEmail(), login.getMemPassword());
        if (memberOptional != null) {
            // 當會員存在執行以下
            Members member = memberOptional;
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            System.out.println(member.getMemNo());
            session.setAttribute("memNo", member.getMemNo()); // 将会员编号存储到 session 中
            System.out.println(session.getAttribute("memNo"));
            // 確認Redis存的sessionId與client的cookie存的一致
            String script = "localStorage.setItem('sessionId', '" + sessionId + "');";
            System.out.println(script);
            // 将sessionId和會員編號存到Redis
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
        // 檢查輸入資料格式正確性,有時間改成方法參數1(BindingResult bindingResult,String email
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation failed" ,errors));
        }
        System.out.println(memberRepository.existsByMemEmail(memberDTO.getMemEmail()));
        // 檢查信箱是否被註冊,已被註冊
        if (memberRepository.existsByMemEmail(memberDTO.getMemEmail())) {
            System.out.println("email vaild...");
            errors = Collections.singletonList("此信箱已被註冊，請勿重複註冊");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Register failed",errors));

        }
        System.out.println("sothing error");
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
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(), errors));
        }
        System.out.println("ready to end...");
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(HttpStatus.OK, "Register successful", Collections.emptyList()));
    }

    // 會員更新資料使用
    @PostMapping("/save")
    @ResponseBody
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
            System.out.println("error...Binding");
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
            System.out.println("error...isEmpty");
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

        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(HttpStatus.OK, "Update successful", Collections.emptyList()));
    }

    // 會員變更密碼使用
    @PostMapping("/changePassword")
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
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(HttpStatus.OK, "Change successful", Collections.emptyList()));
    }

    // 由會員編號找到會員資訊
    @GetMapping("/find-one")
    @ResponseBody
    public MemberDTO findMemById(@RequestParam Integer id,HttpServletRequest request) {
        HttpSession session=request.getSession();
        System.out.println(session);
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
