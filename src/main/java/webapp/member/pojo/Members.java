package webapp.member.pojo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "MEM_NO")
    private Integer memNo;

    @Column(name = "MEM_NAME")
    private String memName;

    @Column(name = "MEM_GENDER")
    private Byte memGender;

    @Column(name = "MEM_PIC")
    private byte[] memPic;

    @Column(name = "MEM_EMAIL")
    private String memEmail;

    @Column(name = "MEM_PASSWORD")
    private String memPassword;

    @Column(name = "MEM_PHONE")
    private String memPhone;

    @Column(name = "MEM_ADDRESS")
    private String memAddress;

    @Column(name = "MEM_BIRTHDAY")
    private Date memBirthday;

    @Column(name = "COUPON")
    private Integer coupon;

    @Column(name = "RESERVE_AUTH")
    private Boolean reserveAuth;

    @Column(name = "MEM_VIO")
    private Integer memVIO;

    @Column(name = "MEM_STATUS")
    private Date memStatus;




}
