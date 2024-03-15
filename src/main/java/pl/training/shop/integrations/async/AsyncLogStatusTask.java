package pl.training.shop.integrations.async;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Component
@Log
@RequiredArgsConstructor
public class AsyncLogStatusTask {

    private final TaskScheduler taskScheduler;

    @Async
    public void log() {
        log.info("Current status: ok (%s)".formatted(Thread.currentThread().getName()));
    }

    @Async("tasksExecutor")
    @SneakyThrows
    public Future<Instant> logWithResult() {
        log.info("Current status: ok (%s)".formatted(Thread.currentThread().getName()));
        Thread.sleep(5_000);
        return completedFuture(Instant.now());
    }

}
