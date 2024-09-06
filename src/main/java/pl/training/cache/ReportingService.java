package pl.training.cache;

import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "reports", keyGenerator = "simpleKeyGenerator")
@Service
@Log
public class ReportingService {

    @Cacheable(/*condition = "#month < 6"*//*, unless = "#month < 6"*/)// (cacheNames = "reports", keyGenerator = "simpleKeyGenerator")
    public double generateMonthlySalesReport(int month, int year) {
        log.info("Generating monthly sales report");
        return (month + year) * 0.3;
    }

    @CacheEvict(/*cacheNames = "reports",*/ allEntries = true)
    public void restart() {
        log.info("Resetting monthly sales cache");
    }

    @CacheEvict(key = "#reportId")
    public void resetReport(String reportId) {
        log.info("Resetting report with id " + reportId);
    }

    @CachePut(key = "#reportId")
    public Double insertReport(String reportId) {
        log.info("Inserting report with id " + reportId);
        return 0.0;
    }

}
