package webapp.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.dto.LoginDTO;
import webapp.employee.service.EmployeeService;

@Controller
@RequestMapping("/emp")
public class EmployeeController {
    final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/login-check")
    @ResponseBody
    public Boolean loginCheck(@RequestBody LoginDTO loginDTO) {
        return employeeService.loginCheck(loginDTO);
    }

    @PostMapping("/save")
    @ResponseBody
    public Boolean saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping("/ls-one")
    @ResponseBody
    public EmployeeDTO findById(@RequestParam Integer id) {
        return employeeService.findById(id);
    }

    @GetMapping("/set-status")
    @ResponseBody
    public Boolean updateStatus(@RequestParam Integer id, @RequestParam Boolean status) {
        return employeeService.updateStatus(id, status);
    }
}
