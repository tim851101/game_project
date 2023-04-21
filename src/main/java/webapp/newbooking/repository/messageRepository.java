package webapp.newbooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class messageRepository {
    public static final String HASH_KEY ="message";

    final  private RedisTemplate<String, Map<Integer, List<String>>> messageTemplate;
    @Autowired
    public messageRepository(RedisTemplate<String, Map<Integer, List<String>>> messageTemplate) {
        this.messageTemplate = messageTemplate;
    }
    public Boolean save(Integer id,List<String> messages){
        messageTemplate.opsForHash().put(HASH_KEY
        ,id.toString()
        ,messages
        );
        return true ;
    }
    public List<String> findByID(Integer id){
        return (List<String>) messageTemplate.opsForHash().get(HASH_KEY,id.toString());
    }
}
