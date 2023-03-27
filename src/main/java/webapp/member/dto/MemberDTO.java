package webapp.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class MemberDTO {

    private Integer memNo;

    private String memName;

    private Byte memGender;

    private String memEmail;

    private String memPassword;

    private String memPhone;

    private String memAddress;

    private Date memBirthday;


    private Integer coupon;


    private Boolean reserveAuth;


    private Integer memVIO;


    private Date memStatus;
}
