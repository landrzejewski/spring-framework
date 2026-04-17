package pl.training;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static pl.training.WebSocketUtils.getSocketId;

@Log
@Component
@RequiredArgsConstructor
public class WebSockerDisconnectListener {

    private final SystemMessageSender sender;
    private final InMemoryChatUserRepository repository;

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        var socketId = getSocketId(event);
        repository.get(socketId)
                .ifPresent(user -> {
                    repository.remove(socketId);
                    log.info("Socket with id: %s is disconnected (username: %s)".formatted(socketId, user.username()));
                    sender.sendToAll("User %s is disconnected".formatted(user.username()));
                });
    }

}
