package webapp.member.service;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.MemberDTO;

@Component
public interface MemberService {

    // @Transactional
    // Boolean addMember(MemberDTO dto);
    @Transactional
    String addMember(MemberDTO createUser);

    String memberLogin(LoginDTO loginDTO);

    @Transactional
    String saveMember(MemberDTO modifyUser);

    MemberDTO findById(Integer id);

    MemberDTO findByMemEmail(String memEmail);

    String getNewPassword(String memEmail);

}
