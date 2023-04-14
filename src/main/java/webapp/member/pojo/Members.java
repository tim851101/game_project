package webapp.member.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webapp.product.pojo.Product;

import java.util.Date;
import java.util.List;

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

    @Column(name = "MEM_NAME")
    private String memName;

    @Column(name = "MEM_GENDER")
    private Byte memGender;

    @Column(name = "MEM_EMAIL",unique = true)
    private String memEmail;

    @Column(name = "MEM_PASSWORD")
    private String memPassword;

    @Column(name = "MEM_PHONE")
    private String memPhone;

    @Column(name = "MEM_ADDRESS")
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




    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(referencedColumnName = "MEM_NO",name = "MEM_NO"),
    name = "Collection",inverseJoinColumns = @JoinColumn(name = "PD_NO",referencedColumnName = "PD_NO"))
    private List<Product> wishLists;



}
