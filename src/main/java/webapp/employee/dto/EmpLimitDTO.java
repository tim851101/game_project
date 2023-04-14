package webapp.employee.dto;

import lombok.Data;

@Data
public class EmpLimitDTO {
    private Integer employeeNo;
    private String employeeName;
    private String employeePhone;
    private String employeeAddress;
    private String employeeEmail;
}
