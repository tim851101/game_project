package webapp.member.service;

import org.springframework.stereotype.Service;
import webapp.member.dto.LoginDTO;
import webapp.member.dto.RegisterDTO;
import webapp.member.pojo.Members;

import java.util.List;


//@Service
public interface MemberService  {

    Boolean addMember(RegisterDTO dto);
    Boolean memberLogin(LoginDTO loginDTO);

    RegisterDTO findById(Integer id);

}
