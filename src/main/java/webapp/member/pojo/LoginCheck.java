package webapp.member.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import webapp.member.dto.MemberDTO;

import java.io.Serializable;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@RedisHash("LOGIN_CHECK")
public class LoginCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String sessionId;

    private MemberDTO memberDTO;


    public LoginCheck(String sessionId) {
        this.sessionId = sessionId;
    }
    // 添加一个接受 String 参数的构造函数或工厂方法
    public static LoginCheck fromSessionId(String sessionId) {
        LoginCheck loginCheck = new LoginCheck();
        loginCheck.setSessionId(sessionId);
        return loginCheck;
    }
}
