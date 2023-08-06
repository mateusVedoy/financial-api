package finances.api.application.service;

import finances.api.infraestructure.redis.CacheAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;

@Component
public class CachingService {

    @Autowired
    private CacheAdapter cacheService;

    public boolean hasCache(String key) {
        if(isEmpty(key))
            return false;
        return cacheService.getCache(key) != null;
    }
    public void deleteCache(String key) {
        if(!isEmpty(key)) {
            cacheService.deleteCache(key);
        }
    }
    public byte[] getCache(String key) {
        if (isEmpty(key))
            return null;
        return cacheService.getCache(key);
    }
    public void setCache(String key, byte[] value) {
        if(!isEmpty(key)){
            cacheService.setCache(key, value);
        }
    }
    private boolean isEmpty(String key) {
        return key == null || key.isEmpty();
    }
}
