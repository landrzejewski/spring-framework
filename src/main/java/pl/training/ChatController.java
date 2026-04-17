package pl.training;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Log
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final InMemoryChatUserRepository repository;
    private final SystemMessageSender sender;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${main-topic}")
    @Setter
    private String mainTopic;
    @Value("${private-topic-prefix}")
    @Setter
    private String privateTopicPrefix;

    @MessageMapping("/chat")
    public void onMessage(ChatMessage chatMessage, @Header("simpSessionId") String socketId){
        var message = chatMessage.withTimestamp(Instant.now());
        if (message.isBroadcast()) {
            messagingTemplate.convertAndSend(mainTopic, message);
        } else  {
            repository.get(socketId)
                    .ifPresent(user ->  sendMessage(user, message));
            repository.findByClientIds(message.getRecipients())
                    .forEach(user ->  sendMessage(user, message));
        }
    }

    private void sendMessage(ChatUser chatUser, ChatMessage chatMessage) {
        messagingTemplate.convertAndSend(privateTopicPrefix + chatUser.privateClientId(), chatMessage);
    }

    /*@MessageMapping("/chat")
    @SendTo("/main")
    public ChatMessage onMessage(ChatMessage message){
        return message.withTimestamp(Instant.now());
    }*/

    @MessageMapping("/user-status")
    public void OnUserUpdate(UserStatus userStatus, @Header("simpSessionId") String socketId) {
        repository.updateStatus(socketId, userStatus);
        sender.sendUserList();
    }


}
