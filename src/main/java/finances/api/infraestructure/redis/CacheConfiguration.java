package finances.api.infraestructure.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static java.lang.Integer.valueOf;

@Configuration
public class CacheConfiguration {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.database}")
    private String redisDatabase;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        var redisConfiguration = new RedisStandaloneConfiguration(redisHost, valueOf(redisPort));
        redisConfiguration.setDatabase(valueOf(redisDatabase));
        var jedisConFactory = new JedisConnectionFactory(redisConfiguration);
        return jedisConFactory;
    }

//    @Bean("redisCacheTemplate")
//    public StringRedisTemplate redisCacheTemplate() {
//        var template = new StringRedisTemplate();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }

    @Bean("redisCacheTemplate")
    public RedisTemplate<String, byte[]> redisCacheTemplate() {
        RedisTemplate<String, byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
