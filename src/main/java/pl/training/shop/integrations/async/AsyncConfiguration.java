package pl.training.shop.integrations.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return Executors.newFixedThreadPool(3);
    }

    @Bean
    public Executor tasksExecutor() {
        return Executors.newFixedThreadPool(3);
    }

}