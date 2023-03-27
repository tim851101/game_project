package webapp.member.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.MemberDTO;
import webapp.member.dto.RegisterDTO;
import webapp.member.pojo.Members;
import webapp.member.repository.MemberRepository;
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

    private MemberDTO memberDTO;
    private LoginDTO loginDTO;

//    private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public Boolean addMember(MemberDTO dto) {
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
    public MemberDTO findById(Integer id) {
        System.out.println(memberRepository.findById(id));
        Optional<Members> optional=memberRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), MemberDTO.class);
        }else {
            return modelMapper.map(new Members(),MemberDTO.class);
        }
    }

    public Boolean saveMember(MemberDTO dto){
        try {
            memberRepository.save(modelMapper.map(dto, Members.class));
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MemberDTO findByMemEmail(String memEmail) {
//        System.out.println(memberRepository.findByMemEmail(memEmail));
//        Optional<Members> optional = memberRepository.findByMemEmail(memEmail);
        return null;
    }

}
