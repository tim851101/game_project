package webapp.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webapp.employee.pojo.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Boolean existsByEmployeeNameAndEmployeePassword(String name, String password);
    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.employeeStatus = :status WHERE e.employeeNo = :id")
    void updateEmployeeStatus(@Param("id") Integer id, @Param("status") Boolean status);
}
