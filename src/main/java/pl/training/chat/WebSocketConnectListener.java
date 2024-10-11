package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Log
@Component
@RequiredArgsConstructor
public class WebSocketConnectListener {

    @EventListener
    public void onConnect(SessionConnectEvent event) {

        log.info(event.toString());
    }

}
