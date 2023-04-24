package webapp.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import webapp.member.service.MemberVaildationRules;

@Data
public class ChangePwdDTO {

    private Integer memNo;

    @NotBlank(groups = {
            MemberVaildationRules.MemChangePassword.class
    },message = "密碼不可為空白")
    private String oldPwd;

    @NotBlank(groups = {
            MemberVaildationRules.MemChangePassword.class
    },message = "密碼不可為空白")
    @Size(groups = {
            MemberVaildationRules.MemChangePassword.class
    },min = 6,max = 15,message = "密碼需介於6~15字符間")
    private String newPwd;

}
