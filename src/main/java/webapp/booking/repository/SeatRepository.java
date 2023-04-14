package webapp.booking.repository;


import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import webapp.booking.pojo.Seat;

@Repository
public class SeatRepository {
    public static final String HASH_KEY = "Seat";
    final private RedisTemplate<String, Map<String, List<Integer> > > seatTemplate;

    @Autowired
    public SeatRepository(RedisTemplate<String, Map<String, List<Integer>>> seatTemplate) {
        this.seatTemplate = seatTemplate;
    }

    public Boolean save(Seat seat) {
        seatTemplate.opsForHash().put(
            HASH_KEY,
            seat.getId(),
            seat.getSeat()
        );
        return true;
    }

    public Boolean save(String id, List<Integer> seats) {
        seatTemplate.opsForHash().put(
            HASH_KEY,
            id,
            seats
        );
        return true;
    }

    public Boolean save(Seat seat, Integer timeout, TimeUnit timeUnit) {
        seatTemplate.expire(HASH_KEY, timeout, timeUnit);
        save(seat);
        return true;
    }

    public List<Integer> findByDate(String date) {
        return (List<Integer>) seatTemplate.opsForHash().get(HASH_KEY, date);
    }
}
