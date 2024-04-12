package pl.training.integration.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Log
@RequiredArgsConstructor
public class LogStatusTask implements ApplicationRunner {

    private final TaskScheduler taskScheduler;

    // https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
    //@Scheduled(cron = "@daily")
    //@Scheduled(cron = "0 */1 * * * *")
    //@Scheduled(fixedRate = 5_000)
    public void task() {
        log.info("Current status: ok (%s)".formatted(Instant.now()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //taskScheduler.scheduleAtFixedRate(this::task, Duration.ofMillis(5_000));
        //var future = taskScheduler.schedule(this::task, new CronTrigger("0 */1 * * * *"));
        //future.cancel(false);
    }

}
