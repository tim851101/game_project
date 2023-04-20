package webapp.member.service;

import org.springframework.stereotype.Component;

// 會員數據驗證規則
@Component
public class MemberVaildationRules {

    // 新增時驗證規則
    public interface MemAdd{

    }

    // 更新時驗證規則,不須含密碼
    public interface MemUpdate{

    }

    public interface MemLogin{

    }

    // 變更密碼時驗證規則
    public interface MemChangePassword{

    }

    // 忘記密碼時寄送臨時密碼驗證規則
    public interface getAuthCode {

    }
}
