package webapp.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp.employee.pojo.Employee;
import webapp.employee.service.EmployeeService;

@Service
public class DatabaseService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initializeDatabase() {

        System.out.println("Initializing database...");
        Employee admin = new Employee().builder()
            .employeeName("Amy")
            .employeePhone("0986787656")
            .employeeAddress("台北市東區興亥路10號")
            .employeeEmail("amy@mail.com")
            .employeePassword(passwordEncoder.encode("admin"))
            .roleNo(1)
            .employeeStatus(true)
            .build();

        Employee user = new Employee().builder()
            .employeeName("John")
            .employeePhone("2234567890")
            .employeeAddress("台北市東區興亥路10號")
            .employeeEmail("john@mail.com")
            .employeePassword(passwordEncoder.encode("user"))
            .roleNo(2)
            .employeeStatus(true)
            .build();

        employeeService.create(admin);
        employeeService.create(user);
        System.out.println("Database initialized!");
    }
}
