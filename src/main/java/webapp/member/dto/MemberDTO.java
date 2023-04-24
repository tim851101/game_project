package webapp.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Data;
import webapp.member.service.MemberVaildationRules;

import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"memPassword"},allowSetters = true) // 設定密碼字段为不允許序列化（不會傳给前端)
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Integer memNo;

    @NotBlank(groups = {
        MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class
    },message = "姓名不可為空白")
    @NotNull(message = "姓名不可為空白")
    @Size(max = 10,message = "姓名超過10字符")
    private String memName;


    @NotNull(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class
    },message = "請選擇性別")
    private Byte memGender;

    @NotBlank(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class,
            MemberVaildationRules.MemLogin.class
    },message = "Email不可為空白")
    @Email(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class,
            MemberVaildationRules.getAuthCode.class,MemberVaildationRules.MemLogin.class
    },regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
    message = "Email格式不正確")
    private String memEmail;

    @NotBlank(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemLogin.class
    },message = "密碼不可為空白")
    @Size(groups = {
            MemberVaildationRules.MemAdd.class
    },min = 6,max = 15,message = "密碼需介於6~15字符間")
    private String memPassword;

    @NotBlank(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class
    },message = "電話不可為空白")
    @Pattern(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class,
    },regexp = "^09\\d{8}$",message = "電話格式請以09xx開頭")
    private String memPhone;

    @NotBlank(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class,
    },message = "地址不可為空白")
    @Pattern(regexp = ".*(市|縣|州|鎮|區|道|路|街|巷)[^市縣區]+(路|街|巷)[^號]+號.*",
            groups = {
                    MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class,
            },message = "請輸入正確地址")
    private String memAddress;

    @Past(groups = {
            MemberVaildationRules.MemAdd.class,MemberVaildationRules.MemUpdate.class,
    },message = "生日不可為未來日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
