package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static pl.training.chat.WebSocketUtils.getAttributes;
import static pl.training.chat.WebSocketUtils.getSocketId;

@Component
@Log
@RequiredArgsConstructor
public class WebSocketDisconnectedListener {

    private final SystemMessageSender systemMessageSender;

    @EventListener
    public void onApplicationEvent(SessionDisconnectEvent event) {
        var socketId = getSocketId(event);
        var username = getAttributes(event).get(socketId);
        if (username != null) {
            log.info("Socket with id %s disconnected (user: %s)".formatted(socketId, username));
            systemMessageSender.send("User %s is now disconnected".formatted(username));
        }
    }

}
