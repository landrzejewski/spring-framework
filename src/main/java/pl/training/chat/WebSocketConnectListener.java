package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import static pl.training.chat.WebSocketUtils.getNativeHeader;
import static pl.training.chat.WebSocketUtils.getSocketId;

@Log
@Component
@RequiredArgsConstructor
public class WebSocketConnectListener {

    private static final String USERNAME_HEADER = "username";
    private static final String CLIENT_ID_HEADER = "clientId";
    private static final String PRIVATE_CLIENT_ID_HEADER = "privateClientId";

    private final InMemoryChatUserRepository userRepository;
    private final SystemMessageSender systemMessageSender;

    @EventListener
    public void onConnect(SessionConnectEvent event) {
        var socketId = getSocketId(event);
        var chatUser = new ChatUser(
                getNativeHeader(event, CLIENT_ID_HEADER),
                getNativeHeader(event, PRIVATE_CLIENT_ID_HEADER),
                getNativeHeader(event, USERNAME_HEADER)
        );
        log.info("Socket with id: %s is connected (username: %s)".formatted(socketId, chatUser.username()));
        userRepository.put(socketId, chatUser);
        systemMessageSender.sendToAll("User %s is connected".formatted(chatUser.username()));
        systemMessageSender.updateUserList();
    }

}
