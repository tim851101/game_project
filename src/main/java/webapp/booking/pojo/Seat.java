package webapp.booking.pojo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Seat")
public class Seat implements Serializable {
    @Id
    private String id; // date: yyyy-mm-dd
    private List<Integer> seat; // length 24
}