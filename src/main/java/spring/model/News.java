package spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity // JPA: mark this class as entity
@Table(name = "NEWS")
public class News {

    @Id
    @Column(name = "NEWS_NO")
    private Integer newsNo;

    @Column(name = "NEWS_TITLE")
    private String newsTitle;

    @Column(name = "NEWS_DESC")
    private String newsDesc;

    @Column(name = "NEWS_STATUS")
    private Boolean newsStatus;
}
