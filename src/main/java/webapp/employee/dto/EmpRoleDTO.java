package webapp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpRoleDTO{

    private Integer employeeNo;
    private String employeeName;
    private String employeePhone;
    private String employeeAddress;
    private String employeeEmail;
    private String employeePassword;
    private Integer roleNo;
    private Boolean employeeStatus;
    private String roleName;
}
