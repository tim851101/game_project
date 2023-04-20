package webapp.member.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import webapp.member.dto.MemberDTO;
import webapp.member.pojo.Members;

import java.io.UnsupportedEncodingException;

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

    Members updatePwd(MemberDTO memberDTO);

    Integer getMemberNoFromSession(String sessionId);

}
