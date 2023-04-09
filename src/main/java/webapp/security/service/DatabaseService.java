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

//        final Employee user1 = new Employee(
//            "Emma", "emma@mail.com", passwordEncoder.encode("111"), 1);
        Employee user1 = new Employee().builder()
            .employeeName("John")
            .employeePhone("1234567890")
            .employeeAddress("Not Gonna Tell You St.")
            .employeeEmail("jhon@mail.com")
            .employeePassword(passwordEncoder.encode("222"))
            .roleNo(1)
            .employeeStatus(true)
            .build();
//        final Employee user2 = new Employee(
//            "Jhon", "jhon@mail.com", passwordEncoder.encode("222"), 1);
//        final Employee admin = new Employee(
//            "Anna", "anna@mail.com", passwordEncoder.encode("333"), 2);

//        admin.addRole(Role.ADMIN);

        System.out.println(employeeService.create(user1));
//        System.out.println(personService.create(user2));
//        System.out.println(personService.create(admin));

        System.out.println("Database initialized!");
    }
}

