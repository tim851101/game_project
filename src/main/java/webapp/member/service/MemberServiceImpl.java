package webapp.member.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Validator;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.member.dto.ChangePwdDTO;
import webapp.member.dto.MemberDTO;
import webapp.member.dto.ReserveAuthDTO;
import webapp.member.pojo.Members;
import webapp.member.repository.MemberRepository;
import webapp.others.service.EmailService;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MemberServiceImpl implements MemberService {

    final MemberRepository memberRepository;

    final ModelMapper modelMapper;

    public MemberServiceImpl(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private MemberDTO memberDTO;
    private ChangePwdDTO changePwdDTO;

    @Autowired
    private Validator validator;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmailService emailServiceImpl;

    @Override
    public Members addMember(MemberDTO memberDTO) {
        String memEmail = memberDTO.getMemEmail();
        if (memberRepository.existsByMemEmail(memEmail)) {
            return null;
        }
        try {
            // 將MemberDTO轉換為Members物件
            Members member = modelMapper.map(memberDTO, Members.class);
            // 将密碼加密後存入資料庫
            member.encryptPassword();
            return memberRepository.save(member);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Members updateMember(MemberDTO memberDTO) {
//        System.out.println("start...1");
//        if (memberDTO == null) {
//            System.out.println("start...2");
//            return null;
//        }
        Members member = memberRepository.findById(memberDTO.getMemNo()).orElse(null);
        if (member == null) {
            System.out.println("start...3");
            return null;
        }

        try {
            System.out.println("start...4");
            // Keep the existing password if no new password is provided
            if (memberDTO.getMemPassword() == null) {
                memberDTO.setMemPassword(member.getMemPassword());
            }
            System.out.println("start...5");
            return memberRepository.save(modelMapper.map(memberDTO, Members.class));
        } catch (Exception e) {
            System.out.println("start...6");
            e.printStackTrace();
        }
        return null;
    }

    // sessionId 跟 Member資料到redis
    public Members toLogin(String email,String password){
        Members member = memberRepository.findByMemEmail(email);
        if (member != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, member.getMemPassword())) {
                return member;
            }
        }
        return null;
    }

    // 會員登入成功將sessionId及會員資料存在Redis
    public static final String HASH_KEY = "LOGIN_CHECK";

    public static byte[] toUtf8ByteArray(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }
    @Override
    public void saveSessionToRedis(String sessionId, Members member) throws UnsupportedEncodingException {
        String hashKey=HASH_KEY+":"+sessionId;

        // 將sessionId與會員編號存到Redis
        Map<String, Object> data = new HashMap<>();
        data.put(hashKey, member.getMemNo());
        redisTemplate.opsForValue().set(hashKey, data.toString());
        // 設置過期時間
        redisTemplate.expire(hashKey, 3, TimeUnit.DAYS);

    }


    @Override
    public void deleteSessionFromRedis(String sessionId){
        String hashKey=HASH_KEY+":"+sessionId;
        redisTemplate.delete(hashKey);
    }

    // 透過seesionId取會員編號
    @Override
    public Integer getMemberNoFromSession(String sessionId) {
        String hashKey = HASH_KEY + ":" + sessionId;
        // 使用JSESSIONID找Redis中的客戶登入時的sessionId進而取得會員號碼
        String memNoStr = (String) redisTemplate.opsForValue().get(hashKey);
        if (memNoStr == null || memNoStr.isEmpty()) {
            return null;
        }
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

    @Override
    public MemberDTO findById(Integer id) {
        System.out.println(memberRepository.findById(id));
        Optional<Members> optional=memberRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), MemberDTO.class);
        }else {
            return modelMapper.map(new Members(), MemberDTO.class);
        }
    }

    @Override
    public MemberDTO findByMemEmail(String memEmail) {
        Members member = memberRepository.findByMemEmail(memEmail);
        if (member == null) {
            return null;
        }
        MemberDTO memberDTO = new MemberDTO();
        modelMapper.map(member, memberDTO);
        return memberDTO;
    }

    @Override
    public Members updatePwd(MemberDTO memberDTO){
        return memberRepository.save(modelMapper.map(memberDTO, Members.class));
    }

    @Override
    public String getNewPassword(String memEmail) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(memEmail, JsonObject.class);

        JsonElement emailElement = jsonObject.get("email");
        if (emailElement == null || emailElement.isJsonNull()) {
            return gson.toJson("請確認您所註冊Email");
        }

        String email = emailElement.getAsString();
        System.out.println(email);
        String msg;

        // 會員存在產生臨時密碼
        if (memberRepository.existsByMemEmail(email)) {
            Members member = memberRepository.findByMemEmail(email);
            System.out.println(member);
            String newPassword = genAuthCode();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            member.setMemPassword(encoder.encode(newPassword));
            memberRepository.save(member);
            try {
                msg = gson.toJson("臨時密碼已寄送");
                emailServiceImpl.sendPassword(email, newPassword);
                return msg;
            } catch (Exception e) {
                msg = gson.toJson("請確認您所註冊Email");
                e.printStackTrace();
                return msg;
            }
        } else {
            msg = gson.toJson("請確認您所註冊Email");
            return msg;
        }
    }

    public static String genAuthCode() {
        // 0~9 => 48-57 | A~Z => 65-90 | a~z => 97~122
        char[] verificationCode = new char[8];
        for (int i = 0; i < verificationCode.length; i++) {
            int random = (int) (Math.random() * 75) + 48;
            if ((random >= 48 && random <= 57) || (random >= 65 && random <= 90)
                    || (random >= 97 && random <= 122)) {
                verificationCode[i] = (char) random;
            } else {
                i--;
            }
        }
        String verifiyString = new String(verificationCode);
        System.out.println(verifiyString);
        return verifiyString;
    }


    @Override
    public String getJsessionIdFromCookie(HttpServletRequest request) {
        // 由 header 獲取 cookie
        String cookieHeader = request.getHeader("Cookie");
        if (cookieHeader == null) {
            return null; // 如果 Cookie 不存在，返回 null
        }

        // 解析 Cookie 字符串，將每個 Cookie 鍵值對解析為一個 Cookie
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
            return null; // 如果 JSESSIONID Cookie 不存在，返回 null
        }
        return jsessionId;
    }

    @Override
    public List<MemberDTO> findAllDto() {
        return memberRepository.findAllDto();
    }

    @Override
    public Boolean saveAllAuth(List<ReserveAuthDTO> dtoList) {
        try {
            for (ReserveAuthDTO dto : dtoList) {
                memberRepository.updateReserveAuth(dto.getMemNo(), dto.getReserveAuth());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
