package webapp.employee.repository;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VerifyCodeRepository {

    final private RedisTemplate<String, String> verifyCodeTemplate;

    @Autowired
    public VerifyCodeRepository(RedisTemplate<String, String> verifyCodeTemplate) {
        this.verifyCodeTemplate = verifyCodeTemplate;
    }

    public void saveVerifyCode(String email, String code) {
        verifyCodeTemplate.opsForValue().set(email, code);
        verifyCodeTemplate.expire(email, 3, TimeUnit.MINUTES);
    }

    public String findVerifyCode(String email) {
        return verifyCodeTemplate.opsForValue().get(email);
    }
}
