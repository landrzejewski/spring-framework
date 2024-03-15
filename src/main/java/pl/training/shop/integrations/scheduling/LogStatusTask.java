package pl.training.shop.integrations.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Log
@RequiredArgsConstructor
public class LogStatusTask implements ApplicationRunner {

    private final TaskScheduler taskScheduler;

    // https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
    // @Scheduled(cron = "@daily")
    // @Scheduled(cron = "0 */1 * * * *")
    // @Scheduled(fixedRate = 5_000)
    public void log() {
        log.info("Current status: ok (%s)".formatted(Instant.now()));
    }

    @Override
    public void run(ApplicationArguments args) {
        var future = taskScheduler.scheduleAtFixedRate(this::log, Duration.ofMillis(5_000));
        // future.cancel(false);
        // taskScheduler.schedule(this::log, new CronTrigger("0 15 9-17 * * MON-FRI"));
    }

}
