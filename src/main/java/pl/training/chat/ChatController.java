package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import static java.time.Instant.now;

@Log
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SystemMessageSender systemMessageSender;
    private final InMemoryChatUserRepository userRepository;

    @Value("${main-topic}")
    @Setter
    private String mainTopic;
    @Value("${private-topic-prefix}")
    @Setter
    private String privateTopicPrefix;

    @MessageMapping("/chat")
    public void onMessage(ChatMessage chatMessage, @Header("simpSessionId") String socketId/*, SimpMessageHeaderAccessor headerAccessor*/) {
        /*var attributes = headerAccessor.getSessionAttributes();*/
        var message = chatMessage.withTimestamp(now());
        if (message.isBroadcast()) {
            messagingTemplate.convertAndSend(mainTopic, message);
        } else {
            userRepository.get(socketId).ifPresent(user -> sendMessage(user, message));
            userRepository.findByClientIds(message.getRecipients()).forEach(user -> sendMessage(user, message));
        }
    }

    private void sendMessage(ChatUser user, ChatMessage message) {
        messagingTemplate.convertAndSend(privateTopicPrefix + user.privateClientId(), message);
    }

    @MessageMapping("/users")
    public void onUserUpdate(UserStatus userStatus, @Header("simpSessionId") String socketId) {
        userRepository.updateStatus(socketId, userStatus.isVisible());
        systemMessageSender.updateUserList();
    }

    /*@MessageMapping("/chat")
    @SendTo("/main")
    public ChatMessage onMessage(ChatMessage chatMessage) {
        return chatMessage.withTimestamp(now());
    }*/

}
