package pl.training.jms;

import lombok.extern.java.Log;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class JmsMessageListener {

    @JmsListener(destination = "messages")
    public void onMessage(MessageDto message) {
        log.info("New JMS message: " + message);
    }

}
