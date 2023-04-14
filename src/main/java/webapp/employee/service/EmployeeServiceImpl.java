package webapp.employee.service;

import java.util.List;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.employee.dto.EmpLimitDTO;
import webapp.employee.dto.EmpRoleDTO;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.dto.LoginDTO;
import webapp.employee.pojo.Employee;
import webapp.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper,
                               PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT);
//        this.passwordEncoder = passwordEncoder;
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
            Employee emp = modelMapper.map(dto, Employee.class);
            emp.setEmployeePassword(passwordEncoder.encode(dto.getEmployeePassword()));
            create(emp);
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

    public void updateEmployeePartial(EmpLimitDTO partial) {
        employeeRepository.updateEmployeePartial(partial.getEmployeeNo(), partial.getEmployeeName(),
            partial.getEmployeePhone(), partial.getEmployeeAddress(), partial.getEmployeeEmail());
    }

    // login
    public Employee findByEmail(String email) {
        return employeeRepository
            .findByEmployeeEmail(email)
            .orElse(new Employee());
    }

    public Employee create(Employee employee) {
        if (!isDupliacteEmail(employee)) {
            return employeeRepository.save(employee);
        }
        else {
            return new Employee();
        }
    }

    private Boolean isDupliacteEmail(Employee employee) {
        final String email = employee.getEmployeeEmail();
        if (email != null && email.length() > 0) {
            final Integer id = employee.getEmployeeNo();
            final Employee p = findByEmail(email);
            if (p != null && Objects.equals(p.getEmployeeEmail(), email) && !Objects.equals(p.getEmployeeNo(), id)) {
                return true;
            }
        }
        return false;
    }
}
