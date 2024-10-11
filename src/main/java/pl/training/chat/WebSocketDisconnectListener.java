package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Log
@Component
@RequiredArgsConstructor
public class WebSocketDisconnectListener {

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        log.info(event.toString());
    }

}
