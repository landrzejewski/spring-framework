package pl.training.integration.kafka;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class KafkaMessageListener {

    @KafkaListener(topics = "messages")
    public void onMessage(MessageDto message) {
        log.info("New Kafka message: " + message);
    }

}
