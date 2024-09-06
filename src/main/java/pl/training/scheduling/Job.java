package pl.training.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
@Log
@RequiredArgsConstructor
public class Job implements ApplicationRunner {

    private final TaskScheduler taskScheduler;

    // https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
    // @Scheduled(cron = "@daily")
    // @Scheduled(cron = "0 * * * * *")
    // @Scheduled(fixedRate = 5_000)
    public void run() {
        log.info("Job started");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // var scheduleFuture = taskScheduler.scheduleAtFixedRate(this::run, Duration.ofMillis(5_000));
        // var scheduleFuture = taskScheduler.schedule(this::run, new CronTrigger("0 * * * * *"));
        // scheduleFuture. cancel(false);
    }

}
