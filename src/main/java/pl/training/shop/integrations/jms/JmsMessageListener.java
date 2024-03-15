package pl.training.shop.integrations.jms;

import lombok.extern.java.Log;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class JmsMessageListener {

    @JmsListener(destination = "messages"/*, containerFactory = "trainingJmsContainerFactory"*/)
    public void onMessage(MessageDto messageDto) {
        log.info("New JMS message: " + messageDto);
    }

}
