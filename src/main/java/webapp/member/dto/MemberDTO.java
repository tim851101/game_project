package webapp.member.dto;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Data
public class MemberDTO {

    private Integer memNo;

    @NotBlank(message = "姓名不可為空白")
    @Size(max = 10,message = "姓名超過10字符")
    private String memName;

    @NotNull(message = "請選擇性別")
    private Byte memGender;

    @NotBlank(message = "Email不可為空白")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",message = "Email格式不正確")
    private String memEmail;

    @NotBlank(message = "密碼不可為空白")
//    @Size(min = 6,max = 15,message = "密碼需介於6~15字符間")
    private String memPassword;

    @NotBlank(message = "電話不可為空白")
    @Pattern(regexp = "^09\\d{8}$",message = "電話格式請以09xx開頭")
    private String memPhone;

    @NotBlank(message = "地址不可為空白")
    @Pattern(regexp = ".*(市|縣|州|鎮|區|道|路|街|巷)[^市縣區]+(路|街|巷)[^號]+號.*",message = "請輸入正確地址")
    private String memAddress;

    private Date memBirthday;

    private Integer coupon;

    private Boolean reserveAuth;

    private Integer memVIO;

    private Date memStatus;

//    public void setMemPassword(String password){
//        // 加密
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        this.memPassword=passwordEncoder.encode(password);
//    }
//
//    @PrePersist
//    @PreUpdate
//    public void hashPassword() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        this.setMemPassword(passwordEncoder.encode(this.getMemPassword()));
//    }
}
