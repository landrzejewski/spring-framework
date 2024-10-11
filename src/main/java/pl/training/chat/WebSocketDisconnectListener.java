package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static pl.training.chat.WebSocketUtils.getSocketId;

@Log
@Component
@RequiredArgsConstructor
public class WebSocketDisconnectListener {

    private final SystemMessageSender systemMessageSender;
    private final InMemoryChatUserRepository userRepository;

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        var socketId = getSocketId(event);
        userRepository.get(socketId).ifPresent(chatUser -> {
            userRepository.remove(socketId);
            log.info("Socket with id: %s is disconnected (username: %s)".formatted(socketId, chatUser.username()));
            systemMessageSender.sendToAll("User %s is disconnected".formatted(chatUser.username()));
        });
        systemMessageSender.updateUserList();
    }

}
