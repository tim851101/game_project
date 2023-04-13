package webapp.others.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import webapp.others.pojo.News;

import java.util.List;

@Repository
public class NewsRepository {

    public static final String HASH_KEY = "NEWS";

    @Autowired
    private RedisTemplate redisTemplate;

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
}
