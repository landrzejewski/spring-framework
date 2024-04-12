package pl.training.integration.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@Log
@RequiredArgsConstructor
public class CalculatorExamples implements ApplicationRunner {

    private final Calculator calculator;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("###################################################");
        log.info("1) calculate sum " + calculator.add(1, 2));
/*        log.info("2) calculate sum " + calculator.add(1, 2));
        calculator.rest();
        log.info("3) calculate sum " + calculator.add(1, 2));
        calculator.rest("add[1, 2]");
        log.info("4) calculate sum " + calculator.add(1, 2));
        calculator.put("add[1, 2]", 0);
        log.info("5) calculate sum " + calculator.add(1, 2));*/
        log.info("###################################################");
        /*var cache = cacheManager.getCache("results");
        if (cache != null) {
            cache.clear();
        }*/
    }

}
