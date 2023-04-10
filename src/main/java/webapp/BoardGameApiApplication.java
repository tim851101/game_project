package webapp;

import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Mono;


@SpringBootApplication
@ComponentScan(
    basePackages = {"core.config", "webapp"},
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "webapp.*.pojo.*"
    ))
public class BoardGameApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BoardGameApiApplication.class, args);
//         SpringApplication.exit(SpringApplication.run(BoardGameApiApplication.class, args));
    }

//    @Value("${redis.host}")
//    private String redisHostName;
//
//    @Value("${redis.port}")
//    private Integer redisPort;
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//        redisConfig.setHostName(redisHostName);
//        redisConfig.setPort(redisPort);
//        return new JedisConnectionFactory(redisConfig);
//    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

    @Override
    public void run(String... args) throws Exception {

    }
}

