package webapp.member.service;

import org.springframework.stereotype.Component;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.MemberDTO;

@Component
public interface MemberService  {

    Boolean addMember(MemberDTO dto);
    Boolean memberLogin(LoginDTO loginDTO);

    Boolean saveMember(MemberDTO dto);

    MemberDTO findById(Integer id);

    MemberDTO findByMemEmail(String memEmail);



}
