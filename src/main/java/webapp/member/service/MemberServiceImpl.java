package webapp.member.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.MemberDTO;
import webapp.member.pojo.Members;
import webapp.member.repository.MemberRepository;
import webapp.others.service.EmailService;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService {

    final MemberRepository memberRepository;
    // private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    final ModelMapper modelMapper;

    public MemberServiceImpl(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private MemberDTO memberDTO;
    private LoginDTO loginDTO;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private Validator validator;

    @Autowired
    private EmailService emailServiceImpl;

    @Override
    public String addMember(MemberDTO user) {
        if (memberRepository.existsByMemEmail(user.getMemEmail())) {
            System.out.println("電子信箱已存在，請勿重複註冊");
            return "電子信箱已存在，請勿重複註冊";
        }
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<MemberDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage() + "</br>");
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
        user.setMemPassword(passwordEncoder.encode(user.getMemPassword()));
        memberRepository.save(modelMapper.map(user, Members.class));
        Gson gson = new Gson();
        String successMsg = gson.toJson("會員註冊成功");
        System.out.println(successMsg);
        return successMsg;
    }

    // @Override
    // public Boolean addMember(MemberDTO dto) {
    // // 驗證輸入資料欄位
    // try {
    // memberRepository.save(modelMapper.map(dto,Members.class));
    // return true;
    // }catch (Exception e) {
    // e.printStackTrace();
    // return false;
    // }
    // }

    @Override
    public String memberLogin(LoginDTO loginDTO) {
        if (memberRepository.existsByMemEmail(loginDTO.getMemEmail())) {
            Members member = memberRepository.findByMemEmail(loginDTO.getMemEmail());
            if (passwordEncoder.matches(loginDTO.getMemPassword(), member.getMemPassword())) {
                return "登入成功";
            } else {
                return "帳號密碼錯誤";
            }
        } else {
            return "帳號密碼錯誤";
        }
    }

    @Override
    public MemberDTO findById(Integer id) {
        System.out.println(memberRepository.findById(id));
        Optional<Members> optional = memberRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), MemberDTO.class);
        } else {
            return modelMapper.map(new Members(), MemberDTO.class);
        }
    }

    @Override
    public String saveMember(MemberDTO user) {
        String oldEmail = memberRepository.findById(user.getMemNo()).get().getMemEmail();
        // System.out.println(oldEmail instanceof String);
        // System.out.println(user.getMemEmail() instanceof String);
        if (!oldEmail.equals(user.getMemEmail()) && memberRepository.existsByMemEmail(user.getMemEmail())) {
            System.out.println("電子信箱已存在，請勿重複註冊");
            return "電子信箱已存在，請勿重複註冊";
        }
        // 使用原有密碼跳過驗證設定
        Members member = memberRepository.findByMemEmail(user.getMemEmail());
        user.setMemPassword(member.getMemPassword());
        // 資料驗證錯誤訊息
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<MemberDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage() + "</br>");
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
        // 儲存修改資料
        memberRepository.save(modelMapper.map(user, Members.class));
        Gson gson = new Gson();
        String successMsg = gson.toJson("會員資料修改成功");
        System.out.println(successMsg);
        return successMsg;
    }

    @Override
    public MemberDTO findByMemEmail(String memEmail) {

        return modelMapper.map(memberRepository.findByMemEmail(memEmail), MemberDTO.class);

    }

    @Override
    public String getNewPassword(String memEmail) {
        // 將傳進的email json字串轉乘String
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(memEmail, JsonObject.class);
        String email = jsonObject.get("memEmail").getAsString();
        System.out.println(email);
        String msg;

        MemberDTO user = new MemberDTO();
        System.out.println();
        // 會員存在產生臨時密碼
        if (memberRepository.existsByMemEmail(email)) {
            Members member = memberRepository.findByMemEmail(email);
            System.out.println(member);
            BeanUtils.copyProperties(member, user);
            String newPassword = genAuthCode();
            user.setMemPassword(passwordEncoder.encode(newPassword));
            memberRepository.save(modelMapper.map(user, Members.class));
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
        return Arrays.toString(verificationCode);
    }

}
