package webapp.member.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import webapp.product.pojo.Product;

import java.util.Date;
import java.util.List;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "MEMBERS")
@JsonIgnoreProperties(value = {"memPassword"},allowSetters = true) // 設定密碼字段为不允許序列化（不會傳给前端)
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEM_NO")
    private Integer memNo;

    @Column(name = "MEM_NAME")
    private String memName;

    @Column(name = "MEM_GENDER")
    private Byte memGender;

    @Column(name = "MEM_EMAIL",unique = true)
    private String memEmail;

    @Column(name = "MEM_PASSWORD")
//    @JsonIgnore
    private String memPassword;

    @Column(name = "MEM_PHONE")
    private String memPhone;

    @Column(name = "MEM_ADDRESS")
    private String memAddress;

    @Temporal(TemporalType.DATE)
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

    // 密碼加解密處理
    public void encryptPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.memPassword = encoder.encode(this.memPassword);
    }

    // 密碼驗證
    public boolean checkPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, this.memPassword);
    }

//    @ManyToMany
//    @JoinTable(joinColumns = @JoinColumn(referencedColumnName = "MEM_NO",name = "MEM_NO"),
//    name = "Collection",inverseJoinColumns = @JoinColumn(name = "PD_NO",referencedColumnName = "PD_NO"))
//    private List<Product> wishLists;

}
