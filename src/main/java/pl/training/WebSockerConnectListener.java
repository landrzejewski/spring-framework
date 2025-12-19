package pl.training;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import static pl.training.WebSocketUtils.getNativeHeader;
import static pl.training.WebSocketUtils.getSocketId;

@Log
@Component
@RequiredArgsConstructor
public class WebSockerConnectListener {

    private static final String USERNAME_HEADER = "username";
    private static final String CLIENT_ID_HEADER = "clientId";
    private static final String PRIVATE_CLIENT_ID_HEADER = "privateClientId";

    private final SystemMessageSender sender;
    private final InMemoryChatUserRepository repository;

    @Async
    @EventListener
    public void onConnect(SessionConnectEvent event) {
        var socketId = getSocketId(event);
        var username = getNativeHeader(event, USERNAME_HEADER);
        var clientId = getNativeHeader(event, CLIENT_ID_HEADER);
        var privateClientId = getNativeHeader(event, PRIVATE_CLIENT_ID_HEADER);
        var user = new ChatUser(clientId, privateClientId, username, new UserStatus(true, false));
        repository.put(socketId, user);
        log.info("Socket with id: %s is connected (username: %s)".formatted(socketId, username));
    }

}
