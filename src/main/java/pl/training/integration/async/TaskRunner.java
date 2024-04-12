package pl.training.integration.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Log
@RequiredArgsConstructor
public class TaskRunner implements ApplicationRunner {

    private final AsyncLogStatusTask task;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Executing async task (%s)".formatted(Thread.currentThread().getName()));
        task.task();
        log.info("After task execution (%s)".formatted(Thread.currentThread().getName()));

        log.info("Executing async task (%s)".formatted(Thread.currentThread().getName()));
        var resultFuture = task.taskWithResult();
        log.info("Is finished: " + resultFuture.isDone());
        log.info("After task execution (%s)".formatted(Thread.currentThread().getName()));
        log.info("Result: " + resultFuture.get());
    }

}
