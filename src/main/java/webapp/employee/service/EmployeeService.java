package webapp.employee.service;

import java.util.List;
import webapp.employee.dto.EmpLimitDTO;
import webapp.employee.dto.EmpRoleDTO;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.dto.LoginDTO;
import webapp.employee.pojo.Employee;

public interface EmployeeService {
    Employee findByEmail(String email);
    Boolean loginCheck(LoginDTO loginDTO);
    EmployeeDTO findById(Integer id);
    Boolean saveEmployee(EmployeeDTO dto);
    Boolean updateStatus(Integer id, Boolean status);
    List<EmployeeDTO> findAllDTO();
    EmpRoleDTO findJoinRoleById(Integer id);
    Integer savePwdById(String pwd, Integer id);
    String findPwdById(Integer id);

    void updateEmployeePartial(EmpLimitDTO empPartial);

    //login
    Employee create(Employee employee);
}
