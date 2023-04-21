package webapp.newbooking.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("message")
public class message implements Serializable {
    @Id
    private Integer id; // date: yyyy-mm-dd
    private List<String> message; // length 24
}
