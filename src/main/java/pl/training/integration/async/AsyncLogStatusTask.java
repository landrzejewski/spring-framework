package pl.training.integration.async;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Component
@Log
@RequiredArgsConstructor
public class AsyncLogStatusTask {

    @SneakyThrows
    @Async
    public void task() {
        Thread.sleep(1_000);
        log.info("Current status: ok (%s, %s)".formatted(Instant.now(), Thread.currentThread().getName()));
    }

    @SneakyThrows
    @Async("importantTaskExecutor")
    public Future<String> taskWithResult() {
        Thread.sleep(1_000);
        log.info("Current status: ok (%s, %s)".formatted(Instant.now(), Thread.currentThread().getName()));
        return completedFuture("OK");
    }

}
