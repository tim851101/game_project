package webapp.others.repository;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import webapp.others.pojo.News;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class NewsRepository {

    public static final String HASH_KEY = "NEWS";

    @Autowired
    private RedisTemplate redisTemplate;

    public News save(News news,Long expireTime) {
        String hashKey=HASH_KEY+":"+news.getNewsTitle();
        ValueOperations<String, News> valueOps = redisTemplate.opsForValue();
        valueOps.set(hashKey, news, expireTime, TimeUnit.SECONDS);
        return news;
    }

    public List<News> findAll() {
        return redisTemplate.opsForValue().multiGet(redisTemplate.keys(HASH_KEY + ":*"));
    }

    public News findNewsById(String id) {
        String inputId=HASH_KEY+":"+id;
        System.out.println("repo...");
        System.out.println(redisTemplate.opsForValue().get(inputId));

        return (News) redisTemplate.opsForValue().get(inputId);
    }

    public String deleteNewsById(String id) {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "刪除成功";
    }


//
//    public static final String NEWS_HASH_KEY = "NEWS";
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public News save(News news, Long expireTime) {
//        String title = news.getNewsTitle();
//        redisTemplate.opsForHash().put(NEWS_HASH_KEY + ":" + title, title, news);
//        redisTemplate.expire(NEWS_HASH_KEY + ":" + title, expireTime, TimeUnit.SECONDS);
//        return news;
//    }
//
//    public List<News> findAll() {
//        List<News> newsList = new ArrayList<>();
//        Set<Object> keys = Collections.singleton(redisTemplate.keys(NEWS_HASH_KEY + ":*"));
//        if (keys != null && !keys.isEmpty()) {
//            redisTemplate.execute(connection -> {
//                for (Object key : keys) {
//                    ScanOptions options = ScanOptions.scanOptions().match(key.toString()).build();
//                    connection.hashCommands().hGetAll(key.toString().getBytes()).forEach((k, v) -> {
//                        News news = (News) redisTemplate.getHashValueSerializer().deserialize(v);
//                        newsList.add(news);
//                    });
//                }
//                return null;
//            }, true);
//        }
//        return newsList;
//    }
//
//    public News findNewsById(String id) {
//        return (News) redisTemplate.opsForHash().get(NEWS_HASH_KEY, id);
//    }
//
//    public String deleteNewsById(String id) {
//        if (redisTemplate.opsForHash().hasKey(NEWS_HASH_KEY, id)) {
//            redisTemplate.opsForHash().delete(NEWS_HASH_KEY, id);
//            return "刪除成功";
//        } else {
//            return "該新聞不存在";
//        }
//    }
}
