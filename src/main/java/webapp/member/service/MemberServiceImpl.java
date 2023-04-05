package webapp.member.service;

import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.MemberDTO;
import webapp.member.pojo.Members;
import webapp.member.repository.MemberRepository;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService{

    final MemberRepository memberRepository;
    //    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    final ModelMapper modelMapper;

    public MemberServiceImpl(MemberRepository memberRepository,ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private MemberDTO memberDTO;
    private LoginDTO loginDTO;

    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    private Validator validator;

//    @Autowired
//    private MemberDTO user;

    @Override
    public String addMember(MemberDTO user) {
        if (memberRepository.existsByMemEmail(user.getMemEmail())){
            System.out.println("電子信箱已存在，請勿重複註冊");
            return "電子信箱已存在，請勿重複註冊";
        }
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<MemberDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()+"</br>");
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
        user.setMemPassword(passwordEncoder.encode(user.getMemPassword()));
        memberRepository.save(modelMapper.map(user,Members.class));
        Gson gson = new Gson();
        String successMsg = gson.toJson("會員註冊成功");
        System.out.println(successMsg);
        return successMsg;
    }

//    @Override
//    public Boolean addMember(MemberDTO dto) {
//        // 驗證輸入資料欄位
//        try {
//            memberRepository.save(modelMapper.map(dto,Members.class));
//            return true;
//        }catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    @Override
    public String memberLogin(LoginDTO loginDTO) {
        if (memberRepository.existsByMemEmail(loginDTO.getMemEmail())){
            Members member=memberRepository.findByMemEmail(loginDTO.getMemEmail());
            if (passwordEncoder.matches(loginDTO.getMemPassword(),member.getMemPassword())){
                return "登入成功";
            }else {
                return "帳號密碼錯誤";
            }
        }else {
            return "帳號密碼錯誤";
        }
    }

    @Override
    public MemberDTO findById(Integer id) {
        System.out.println(memberRepository.findById(id));
        Optional<Members> optional=memberRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), MemberDTO.class);
        }else {
            return modelMapper.map(new Members(),MemberDTO.class);
        }
    }

    @Override
    public String saveMember(MemberDTO user) {
        String oldEmail=memberRepository.findById(user.getMemNo()).get().getMemEmail();
//        System.out.println(oldEmail instanceof String);
//        System.out.println(user.getMemEmail() instanceof String);
        if (!oldEmail.equals(user.getMemEmail()) && memberRepository.existsByMemEmail(user.getMemEmail())) {
            System.out.println("電子信箱已存在，請勿重複註冊");
            return "電子信箱已存在，請勿重複註冊";
        }
        // 使用原有密碼跳過驗證設定
        Members member=memberRepository.findByMemEmail(user.getMemEmail());
        user.setMemPassword(member.getMemPassword());
        // 資料驗證錯誤訊息
        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<MemberDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()+"</br>");
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
        // 儲存修改資料
        memberRepository.save(modelMapper.map(user,Members.class));
        Gson gson = new Gson();
        String successMsg = gson.toJson("會員資料修改成功");
        System.out.println(successMsg);
        return successMsg;
    }

    @Override
    public MemberDTO findByMemEmail(String memEmail) {

        return modelMapper.map(memberRepository.findByMemEmail(memEmail), MemberDTO.class);

    }


}
