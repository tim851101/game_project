package webapp.member.pojo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "MEMBERS")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEM_NO",insertable = false)
    private Integer memNo;

    @Column(name = "MEM_NAME" ,nullable = false, length = 10)
    private String memName;

    @Column(name = "MEM_GENDER", nullable = false)
    private Byte memGender;

    @Column(name = "MEM_PIC")
    private byte[] memPic;

    @Column(name = "MEM_EMAIL", nullable = false, length = 50, unique = true)
    private String memEmail;

    @Column(name = "MEM_PASSWORD", nullable = false, length = 40)
    private String memPassword;

    @Column(name = "MEM_PHONE", length = 10)
    private String memPhone;

    @Column(name = "MEM_ADDRESS", nullable = false, length = 100)
    private String memAddress;

    @Column(name = "MEM_BIRTHDAY")
    private Date memBirthday;

    @Column(name = "COUPON",insertable = false)
    private Integer coupon;

    @Column(name = "RESERVE_AUTH",insertable = false)
    private Boolean reserveAuth;

    @Column(name = "MEM_VIO",insertable = false)
    private Integer memVIO;

    @Column(name = "MEM_STATUS")
    private Date memStatus;


    public Members(String memName, Byte memGender, byte[] memPic, String memEmail, String password, String memPhone, String memAddress, Date memBirthday, String encode) {
    }
}
