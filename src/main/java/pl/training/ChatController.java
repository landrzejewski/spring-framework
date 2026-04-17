package pl.training;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Log
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final InMemoryChatUserRepository repository;
    private final SystemMessageSender sender;

    @MessageMapping("/chat")
    @SendTo("/main")
    public ChatMessage onMessage(ChatMessage message){
        return message.withTimestamp(Instant.now());
    }

    @MessageMapping("/user-status")
    public void OnUserUpdate(UserStatus userStatus, @Header("simpSessionId") String socketId) {
        repository.updateStatus(socketId, userStatus);
        sender.sendUserList();
    }


}
