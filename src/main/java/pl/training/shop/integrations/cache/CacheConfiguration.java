package pl.training.shop.integrations.cache;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching//(order = 10_000)
@Configuration
public class CacheConfiguration {

   /* @Bean
    public CacheManager cacheManager() {
        return new TransactionAwareCacheManagerProxy(new ConcurrentMapCacheManager("results"));
    }*/

    /*@Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        var config = RedisCacheConfiguration.defaultCacheConfig();
        config.usePrefix();
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }*/

    /*@Bean
    public HazelcastInstance hazelcastInstance() {
        var config = new ClientConfig();
        config.setClusterName("training");
        config.getNetworkConfig()
                .addAddress("localhost:5701");
        return HazelcastClient.newHazelcastClient(config);
    }*/

    @Bean
    public HazelcastInstance hazelcastInstance() {
        var config = new Config();
        config.getNetworkConfig()
                .setPortAutoIncrement(true)
                .getJoin()
                .getMulticastConfig()
                .setEnabled(true)
                .setMulticastPort(20_000);
        return Hazelcast.newHazelcastInstance(config);
    }

}
