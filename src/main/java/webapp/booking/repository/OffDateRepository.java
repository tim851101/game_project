package webapp.booking.repository;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import webapp.booking.pojo.OffDate;

@Repository
public class OffDateRepository {
    private static final String HASH_KEY = "vacation";
    final private RedisTemplate<String, Map<String, List<String>>> offDateTemplate;

    @Autowired
    public OffDateRepository(RedisTemplate<String, Map<String, List<String>>> offDateTemplate) {
        this.offDateTemplate = offDateTemplate;
    }


    public Boolean save(OffDate offDate){
        offDateTemplate.opsForHash().put(HASH_KEY, offDate.getId(), offDate.getDate());
        return true;
    }

    public List<String> findById(String id){
        return (List<String>) offDateTemplate.opsForHash().get(HASH_KEY, id);
    }
}
