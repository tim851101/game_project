package webapp.employee.service;

import java.util.List;
import webapp.employee.dto.EmpLimitDTO;
import webapp.employee.dto.EmpRoleDTO;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.dto.LoginDTO;
import webapp.employee.pojo.Employee;
import webapp.security.dto.AuthRequestDTO;

public interface EmployeeService {
    Employee findByEmail(String email);
    Boolean loginCheck(LoginDTO loginDTO);
    EmployeeDTO findById(Integer id);
    Boolean saveEmployee(EmployeeDTO dto);
    Boolean updateStatus(Integer id, Boolean status);
    List<EmployeeDTO> findAllDTO();
    EmpRoleDTO findJoinRoleById(Integer id);
    Boolean savePwdByEmail(AuthRequestDTO dto);
    String findPwdById(Integer id);
    Boolean checkPwd(AuthRequestDTO dto);
    EmployeeDTO findDTOByEmail(String email);
    Boolean updateEmployeePartial(EmpLimitDTO empPartial);
    Boolean emailDuplicateCheck(String email);
    Employee create(Employee employee);
}
