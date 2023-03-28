package webapp.employee.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "EMPLOYEE")
public class Employee {
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

    @Column(name = "EMPLOYEE_EMAIL", nullable = false)
    private String employeeEmail;

    @Column(name = "EMPLOYEE_PASSWORD", nullable = false)
    private String employeePassword;

    @Column(name = "ROLE_NO")
    private Integer roleNo;

    @Column(name = "EMPLOYEE_STATUS")
    private Boolean employeeStatus;
}