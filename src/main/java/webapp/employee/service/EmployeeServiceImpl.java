package webapp.employee.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import webapp.employee.dto.EmpRoleDTO;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.dto.LoginDTO;
import webapp.employee.pojo.Employee;
import webapp.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    final ModelMapper modelMapper;
    final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public Boolean loginCheck(LoginDTO loginDTO) {
        return employeeRepository
            .existsByEmployeeNameAndEmployeePassword(
                loginDTO.getEmployeeName(), loginDTO.getEmployeePassword());
    }

    public EmployeeDTO findById(Integer id) {
//        Optional<Employee> optional = employeeRepository.findById(id);
//        if (optional.isPresent()) {
//            return modelMapper.map(optional.get(), EmployeeDTO.class);
//        } else {
//            return modelMapper.map(new Employee(), EmployeeDTO.class);
//        }
        return employeeRepository.findByEmpId(id);
    }

    public Boolean saveEmployee(EmployeeDTO dto) {
        try {
            employeeRepository.save(modelMapper.map(dto, Employee.class));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean updateStatus(Integer id, Boolean status) {
        try {
            employeeRepository.updateEmployeeStatus(id, status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<EmployeeDTO> findAllDTO() {
        return employeeRepository.findAllDTO();
    }

    @Override
    public EmpRoleDTO findJoinRoleById(Integer id) {
        return employeeRepository.findJoinRoleById(id);
    }

    @Override
    public Integer savePwdById(String pwd, Integer id) {
        try {
            return employeeRepository.savePwdById(pwd, id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String findPwdById(Integer id) {
        return employeeRepository.findPwdById(id);
    }
}
