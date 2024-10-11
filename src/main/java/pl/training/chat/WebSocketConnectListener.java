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

    private final InMemoryChatUserRepository userRepository;

    @EventListener
    public void onConnect(SessionConnectEvent event) {
        var socketId = getSocketId(event);
        var chatUser = new ChatUser(getNativeHeader(event, CLIENT_ID_HEADER), getNativeHeader(event, USERNAME_HEADER));
        userRepository.put(socketId, chatUser);

    }

}
