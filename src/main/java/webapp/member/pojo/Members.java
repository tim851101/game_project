package webapp.member.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "MEM_NO")
    private Integer memNo;

    @Column(name = "MEM_NAME",nullable = true)
    private String memName;

    @Column(name = "MEM_GENDER",nullable = true)
    private Byte memGender;

    @Column(name = "MEM_EMAIL",nullable = true)
    private String memEmail;

    @Column(name = "MEM_PASSWORD",nullable = true)
    private String memPassword;

    @Column(name = "MEM_PHONE",nullable = true)
    private String memPhone;

    @Column(name = "MEM_ADDRESS",nullable = true)
    private String memAddress;

    @Column(name = "MEM_BIRTHDAY")
    private Date memBirthday;

    @Column(name = "COUPON",insertable = false,updatable = false)
    private Integer coupon;

    @Column(name = "RESERVE_AUTH",insertable = false,updatable = false)
    private Boolean reserveAuth;

    @Column(name = "MEM_VIO",insertable = false,updatable = false)
    private Integer memVIO;

    @Column(name = "MEM_STATUS",insertable = false,updatable = false)
    private Date memStatus;




}
