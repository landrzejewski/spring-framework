package pl.training.cache;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfiguration {

   /* @Bean
    public CacheManager cacheManager() {
        return new TransactionAwareCacheManagerProxy(new ConcurrentMapCacheManager("reports"));
    }*/

    /*@Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        var config = RedisCacheConfiguration.defaultCacheConfig();
        config.usePrefix();
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }*/

   /* @Bean
    public HazelcastInstance hazelcastInstanceClient() {
        var config = new ClientConfig();
        config.setClusterName("training");
        config.getNetworkConfig()
                .addAddress("localhost:5701");
        return HazelcastClient.newHazelcastClient(config);
    }*/


    @Bean
    public HazelcastInstance hazelcastInstanceClient() {
        var config = new Config();
        config.getNetworkConfig()
                .setPortAutoIncrement(true)
                .getJoin()
                .getMulticastConfig()
                .setEnabled(true)
                .setMulticastPort(20_000);
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstanceClient) {
        return new HazelcastCacheManager(hazelcastInstanceClient);
    }

}
