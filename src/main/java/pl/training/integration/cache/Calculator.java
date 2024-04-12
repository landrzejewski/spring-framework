package pl.training.integration.cache;

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

    @Cacheable(/*value = "results", keyGenerator = "simpleKeyGenerator", condition = "#a > #b"*/)
    public int add(int a, int b) {
        log.info("Calculating sum of " + a + " and " + b);
        return a + b;
    }

    @CacheEvict(allEntries = true)
    public void rest() {
        log.info("Clearing the cache");
    }

    @CacheEvict(key = "#key")
    public void rest(String key) {
        log.info("Clearing the entry with key " + key);
    }

    @CachePut(key = "#key")
    public int put(String key, int value) {
        return value;
    }

}
