package webapp.member.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.RegisterDTO;
import webapp.member.pojo.Members;
import webapp.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

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

    private RegisterDTO userRegister;
    private LoginDTO loginDTO;

    private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public Boolean addMember(RegisterDTO dto) {
        try {
            memberRepository.save(modelMapper.map(dto,Members.class));
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean memberLogin(LoginDTO loginDTO) {
        return memberRepository
                .existsByMemEmailAndMemPassword(
                        loginDTO.getMemEmail(),loginDTO.getMemPassword());
    }

    @Override
    public RegisterDTO findById(Integer id) {
        System.out.println(memberRepository.findById(id));
        Optional<Members> optional=memberRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), RegisterDTO.class);
        }else {
            return modelMapper.map(new Members(),RegisterDTO.class);
        }
    }


//    public String addMember(RegisterDTO registerDTO){
//        Members member=new Members(
//                registerDTO.getMemName(),
//                registerDTO.getMemGender(),
//                registerDTO.getMemPic(),
//                registerDTO.getMemEmail(),
//                registerDTO.getMemPassword(),
//                registerDTO.getMemPhone(),
//                registerDTO.getMemAddress(),
//                registerDTO.getMemBirthday(),
//
////                this.passwordEncoder.encode(registerDTO.getMemPassword())
//
//        );
//        memberRepository.save(member);
//        return member.getMemName();
//    }


}
