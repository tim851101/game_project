package webapp.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import webapp.employee.pojo.Employee;
import webapp.employee.service.EmployeeService;
import webapp.security.dto.AuthRequestDTO;
import webapp.security.dto.AuthResponseDTO;

@Service
public class AuthService {

    @Autowired
    private EmployeeService empService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    public AuthResponseDTO register(RegisterRequestDTO dto) {
//
//        Person person = new Person();
//        person.setName(dto.getName());
//        person.setEmail(dto.getEmail());
//        person.setRoleId(dto.getRoleId());
//        person.setPassword(passwordEncoder.encode(dto.getPassword()));
//
//        person = empService.create(person);
//
//        return new AuthResponseDTO(
//            jwtService.generateToken(person.getEmail()));
//    }

    public AuthResponseDTO authenticate(AuthRequestDTO dto) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword())
        );

        final Employee employee = empService.findByEmail(dto.getEmail());
        return new AuthResponseDTO(
            jwtService.generateToken(employee.getEmployeeEmail()));
    }
}

