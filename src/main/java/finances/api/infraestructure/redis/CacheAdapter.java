package finances.api.infraestructure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;

@Component
public class CacheAdapter {

    @Autowired
    @Qualifier("redisCacheTemplate")
    private RedisTemplate<String, byte[]> redis;

    public byte[] getCache(String key) {
        if (key != null)
            return redis.opsForValue().get(valueOf(key));

        return null;
    }
    public boolean deleteCache(String key) {
        if (key != null)
            return redis.opsForValue().getOperations().delete(valueOf(key));

        return false;
    }
    public void setCache(String key, byte[] value) {
        if (key != null && value != null)
            redis.opsForValue().set(key, value);
    }
}