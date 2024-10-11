package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static java.time.Instant.now;

@Log
@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/main")
    public ChatMessageDto onMessage(ChatMessageDto chatMessageDto) {
        return chatMessageDto.withTimestamp(now());
    }

}
