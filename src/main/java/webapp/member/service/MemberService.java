package webapp.member.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import webapp.member.dto.MemberDTO;
import webapp.member.pojo.Members;

import java.io.UnsupportedEncodingException;
import webapp.member.dto.ReserveAuthDTO;

@Component
public interface MemberService {

    @Transactional
    Members addMember(MemberDTO createUser);

    @Transactional
    Members updateMember(MemberDTO updateUser);


    MemberDTO findById(Integer id);

    MemberDTO findByMemEmail(String memEmail);

    String getNewPassword(String memEmail);

    Members toLogin(String memEmail, String memPassword);

    // 登入成功將sessionId與會員資料存在Redis
    void saveSessionToRedis(String sessionId, Members member) throws UnsupportedEncodingException;

    void deleteSessionFromRedis(String sessionId);
    Members updatePwd(MemberDTO memberDTO);

    Integer getMemberNoFromSession(String sessionId);

    String getJsessionIdFromCookie(HttpServletRequest request);

    List<MemberDTO> findAllDto();

    Boolean saveAllAuth(List<ReserveAuthDTO> dto);
}
