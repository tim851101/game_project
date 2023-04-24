package webapp.employee.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.employee.dto.EmpRoleDTO;
import webapp.employee.dto.EmployeeDTO;
import webapp.employee.pojo.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmployeeEmail(String email);
    Boolean existsByEmployeeNameAndEmployeePassword(String name, String password);
    Boolean existsByEmployeeEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.employeeStatus = :status WHERE e.employeeNo = :id")
    void updateEmployeeStatus(@Param("id") Integer id, @Param("status") Boolean status);

    @Query("SELECT new webapp.employee.dto.EmployeeDTO(employeeNo, employeeName, employeePhone," +
        " employeeAddress, employeeEmail, employeePassword, roleNo, employeeStatus)" +
        " FROM Employee")
    List<EmployeeDTO> findAllDTO();

    @Query("SELECT new webapp.employee.dto.EmployeeDTO(employeeNo, employeeName, employeePhone," +
        " employeeAddress, employeeEmail, employeePassword, roleNo, employeeStatus)" +
        " FROM Employee WHERE employeeNo= :id")
    EmployeeDTO findByEmpId(@Param("id") Integer id);

    @Query(
        "SELECT new webapp.employee.dto.EmpRoleDTO(e.employeeNo, e.employeeName, e.employeePhone, " +
            "e.employeeAddress, e.employeeEmail, e.employeePassword, e.roleNo, e.employeeStatus, r.roleName) " +
            "FROM Employee e JOIN Role r ON e.roleNo = r.roleNo")
    List<EmpRoleDTO> findAllJoinDTO();

    @Query(
        "SELECT new webapp.employee.dto.EmpRoleDTO(e.employeeNo, e.employeeName, e.employeePhone, " +
            "e.employeeAddress, e.employeeEmail, e.employeePassword, e.roleNo, e.employeeStatus, r.roleName) " +
            "FROM Employee e JOIN Role r ON e.roleNo = r.roleNo WHERE e.employeeNo= :id")
    EmpRoleDTO findJoinRoleById(@Param("id") Integer id);

    @Query("SELECT employeePassword FROM Employee WHERE employeeNo= :id")
    String findPwdById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.employeePassword = :password WHERE e.employeeEmail = :email")
    void savePwdByEmail(@Param("email") String email, @Param("password") String pwd);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.employeeName = :employeeName, e.employeePhone = :employeePhone, e.employeeAddress = :employeeAddress, e.employeeEmail = :employeeEmail WHERE e.employeeNo = :employeeNo")
    void updateEmployeePartial(@Param("employeeNo") Integer employeeNo, @Param("employeeName") String employeeName, @Param("employeePhone") String employeePhone, @Param("employeeAddress") String employeeAddress, @Param("employeeEmail") String employeeEmail);

    @Query("SELECT e.employeePassword FROM Employee e WHERE e.employeeEmail = :email")
    String findPwdByEmail(@Param("email") String email);
}
