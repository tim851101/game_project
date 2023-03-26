package webapp.employee.service;

import java.util.List;
import org.springframework.stereotype.Service;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.dto.LoginDTO;

public interface EmployeeService {
    Boolean loginCheck(LoginDTO loginDTO);
    EmployeeDTO findById(Integer id);
    Boolean saveEmployee(EmployeeDTO dto);
    Boolean updateStatus(Integer id, Boolean status);
    List<EmployeeDTO> findAllDTO();
}
