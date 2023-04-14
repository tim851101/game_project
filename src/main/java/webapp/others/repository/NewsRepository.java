package webapp.others.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import webapp.others.pojo.News;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Repository
public class NewsRepository {

    public static final String HASH_KEY = "NEWS";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public News save(News news) {
        String hashKey=HASH_KEY+":"+news.getNewsTitle();
        ValueOperations<String, News> valueOps = redisTemplate.opsForValue();
        valueOps.set(hashKey, news);
        redisTemplate.expireAt(hashKey,news.getDueDate());
        return news;
    }

    public List<News> findAll() {
        return redisTemplate.opsForValue().multiGet(redisTemplate.keys(HASH_KEY + ":*"));
    }

    public News findNewsById(String id) {
        String inputId=HASH_KEY+":"+id;
        System.out.println("finded...");
        System.out.println(redisTemplate.opsForValue().get(inputId));

        return (News) redisTemplate.opsForValue().get(inputId);
    }

    public Boolean deleteNewsById(String id) {
        return redisTemplate.delete(id);
    }

    public News randomSelectOne(){
        List<Object> keys = redisTemplate.opsForValue().multiGet(redisTemplate.keys(HASH_KEY + ":*"));
        if (keys != null && !keys.isEmpty()) {
            int randomIndex = new Random().nextInt(keys.size());
            Object randomKey = keys.get(randomIndex);
            System.out.println(randomKey);
            return (News) randomKey;
        }
        return null;
    }
}
