package webapp.others.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
//@Entity // JPA: mark this class as entity
@RedisHash("NEWS")
//@Table(name = "NEWS")
public class News implements Serializable {
    private static final long serialVersionUID = 1L;

//    @Id
//    @Column(name = "NEWS_NO")
//    private Integer newsNo;


//    @Column(name = "NEWS_TITLE")
    @Id
    private String newsTitle;

//    @Column(name = "NEWS_DESC")
    private String newsDesc;

//    @Column(name = "NEWS_STATUS")
//    private Boolean newsStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date dueDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(newsTitle, news.newsTitle) && Objects.equals(newsDesc, news.newsDesc) && Objects.equals(dueDate, news.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsTitle, newsDesc, dueDate);
    }
}
