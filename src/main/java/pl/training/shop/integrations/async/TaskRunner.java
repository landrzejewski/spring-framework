package pl.training.shop.integrations.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class TaskRunner implements ApplicationRunner {

    private final AsyncLogStatusTask task;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Executing async task (%s)".formatted(Thread.currentThread().getName()));
        // task.log();
        var result = task.logWithResult();
    }

}
