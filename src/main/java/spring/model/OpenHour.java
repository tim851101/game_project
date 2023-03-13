package spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "OPENNING_TIME")// JPA: specify the table this class mapping to
public class OpenHour {

//    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
    @Id // JPA: primary key
    @Column(name = "OPENTIME_NO")// JPA: map to column
    private int openTimeNo;

    @Column(name = "WEEK")
    private byte week;

    @Column(name = "OPENTIME_START")
    private Time openTimeStart;

    @Column(name = "OPENTIME_END")
    private Time openTimeEnd;
}
