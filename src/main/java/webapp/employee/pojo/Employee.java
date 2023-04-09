package webapp.employee.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "EMPLOYEE")
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_NO")
    private Integer employeeNo;

    @Column(name = "EMPLOYEE_NAME", nullable = false)
    private String employeeName;

    @Column(name = "EMPLOYEE_PHONE")
    private String employeePhone;

    @Column(name = "EMPLOYEE_ADDRESS")
    private String employeeAddress;

    @Column(name = "EMPLOYEE_EMAIL", nullable = false, unique = true)
    private String employeeEmail;

    @Column(name = "EMPLOYEE_PASSWORD", nullable = false)
    private String employeePassword;

    @Column(name = "ROLE_NO")
    private Integer roleNo;

    @Column(name = "EMPLOYEE_STATUS")
    private Boolean employeeStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet roleSet = new HashSet<>();
        roleSet.add(new SimpleGrantedAuthority(RoleEnum.fromId(roleNo).name()));
        return roleSet;
    }

    @Override
    public String getPassword() {
        return employeePassword;
    }

    @Override
    public String getUsername() {
        return employeeEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public String toString() {
        return "Person [id=" + employeeNo +
            ", email=" + employeeEmail +
            ", roles=" + roleNo + "]";
    }
}