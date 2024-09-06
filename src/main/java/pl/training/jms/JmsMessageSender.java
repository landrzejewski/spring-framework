package pl.training.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JmsMessageSender implements ApplicationRunner {

    private final JmsTemplate jmsTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // jmsTemplate.send("messages", session -> session.createObjectMessage(new MessageDto("Hello")));
        jmsTemplate.convertAndSend("messages", new MessageDto("Hello"));
    }

}
