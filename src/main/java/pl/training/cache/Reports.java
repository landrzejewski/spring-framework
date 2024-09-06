package pl.training.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

// @Component
@RequiredArgsConstructor
@Log
public class Reports implements ApplicationRunner {

    private final ReportingService reportingService;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(1, 2024));
        log.info("execution 2: " + reportingService.generateMonthlySalesReport(1, 2024));
/*
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(2, 2024));
        reportingService.restart();
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(1, 2024));
        log.info("execution 2: " + reportingService.generateMonthlySalesReport(1, 2024));
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(2, 2024));
        reportingService.resetReport("generateMonthlySalesReport[1, 2024]");
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(1, 2024));
        log.info("execution 2: " + reportingService.generateMonthlySalesReport(1, 2024));
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(2, 2024));
        reportingService.insertReport("generateMonthlySalesReport[1, 2024]");
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(1, 2024));
        log.info("execution 2: " + reportingService.generateMonthlySalesReport(1, 2024));
        log.info("execution 1: " + reportingService.generateMonthlySalesReport(2, 2024));
*/

        var cache = cacheManager.getCache("reports");
        // cache.clear();
        // cache.put("generateMonthlySalesReport[1, 2024]", 1.0);
        // cache.get("generateMonthlySalesReport[2, 2024]", Double.class);
    }

}
