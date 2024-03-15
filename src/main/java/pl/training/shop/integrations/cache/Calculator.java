package pl.training.shop.integrations.cache;

import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@CacheConfig(cacheNames = "results", keyGenerator = "simpleKeyGenerator")
@Component
@Log
public class Calculator {

    @Cacheable(/*value = "results", keyGenerator = "simpleKeyGenerator", cacheManager = "otherCacheManager", condition = "#a > #b"*/)
    public int add(int a, int b) {
        log.info("Calculating sum value for %s and %s".formatted(a, b));
        return a + b;
    }

    @CacheEvict(allEntries = true)
    public void reset() {
        log.info("Cleaning the cache");
    }

    @CacheEvict(key = "#key")
    public void reset(String key) {
        log.info("Cleaning entry with key: " + key);
    }

    @CachePut(key = "#key")
    public int put(String key, int value) {
        return value;
    }

}
