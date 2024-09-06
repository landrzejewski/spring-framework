package pl.training.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

// @Component
@RequiredArgsConstructor
public class KafkaMessageSender implements ApplicationRunner {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kafkaTemplate.send("messages", new MessageDto("Hello"));
    }

}
