package webapp.member.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "SERVICE")
public class Service {

    @Id
    @Column(name = "SERVICE_NO")
    private Integer serviceNo;

    @Column(name = "EMPLOYEE_NO")
    private Integer employeeNo;

    @Column(name = "MEM_NO")
    private Integer memNo;

    @Column(name = "SERVICE_MSG")
    private String serviceMsg;

    @Column(name = "SERVICE_TIME")
    private Date serviceTime;

    @Column(name = "DIALOGUE_DIRECTION")
    private Boolean dialogueDirection;

}
