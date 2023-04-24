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
            .employeeName("林艾美")
            .employeePhone("0988768473")
            .employeeAddress("台北市東區東街88巷6號")
            .employeeEmail("amy@mail.com")
            .employeePassword(passwordEncoder.encode("333"))
            .roleNo(1)
            .employeeStatus(true)
            .build();

        Employee user = new Employee().builder()
            .employeeName("王強尼")
            .employeePhone("0982768364")
            .employeeAddress("台南市北區東街88巷6號")
            .employeeEmail("john@mail.com")
            .employeePassword(passwordEncoder.encode("222"))
            .roleNo(2)
            .employeeStatus(true)
            .build();

        employeeService.create(admin);
        employeeService.create(user);
        System.out.println("Database initialized!");
    }
}
