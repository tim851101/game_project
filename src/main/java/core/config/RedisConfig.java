package core.config;


import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHostName;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Bean

    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHostName);
        redisConfig.setPort(redisPort);
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    @Bean
    public JedisConnectionFactory jedisConnectionFactorySeat() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
        config.setDatabase(5);
        config.setPassword("");

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(15);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(0);
        poolConfig.setMaxWait(Duration.ofSeconds(5));

        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        factory.setPoolConfig(poolConfig); // TODO: deprecated
        return factory;
    }

    @Bean
    public RedisTemplate<String, Map<String, List<Integer>>> seatTemplate() {
        RedisTemplate<String, Map<String, List<Integer>>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactorySeat());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, Map<String, List<String>>> offDateTemplate() {
        RedisTemplate<String, Map<String, List<String>>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactorySeat());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(List.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, String> verifyCodeTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactorySeat());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
}
