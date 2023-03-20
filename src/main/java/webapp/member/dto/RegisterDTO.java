package webapp.member.dto;

import lombok.Data;
import java.util.Date;

@Data
public class RegisterDTO {

    private String memName;
    private Byte memGender;
    private byte[] memPic;
    private String memEmail;
    private String memPassword;
    private String memPhone;
    private String memAddress;
    private Date memBirthday;


}
