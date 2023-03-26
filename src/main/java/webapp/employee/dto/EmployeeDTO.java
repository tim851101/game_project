package webapp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    private Integer employeeNo;
    private String employeeName;
    private String employeePhone;
    private String employeeAddress;
    private String employeeEmail;
    private String employeePassword;
    private Integer roleNo;
    private Boolean employeeStatus;
}