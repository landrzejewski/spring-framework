package pl.training.shop.integrations.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;

// @Component
@RequiredArgsConstructor
@Log
public class CacheExamples implements ApplicationRunner {

    private final Calculator calculator;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("##############################################################");
        log.info("First call result: " + calculator.add(1, 2));
        log.info("Second call result: " + calculator.add(1, 2));
        calculator.reset();
        log.info("Third call result: " + calculator.add(1, 2));
        calculator.reset("add[1, 2]");
        log.info("Fourth call result: " + calculator.add(1, 2));
        log.info("##############################################################");
        /*Optional.ofNullable(cacheManager.getCache("results"))
                .ifPresent(Cache::clear);*/
    }

}
