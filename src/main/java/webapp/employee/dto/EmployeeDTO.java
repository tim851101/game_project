package webapp.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer employeeNo;
    private String employeeName;
    private String employeePhone;

    private String employeeAddress;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "xxx")
    private String employeeEmail;
    private String employeePassword;
    private Integer roleNo;
    private Boolean employeeStatus;
}