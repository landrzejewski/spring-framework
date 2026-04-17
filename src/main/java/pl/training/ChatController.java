package pl.training;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Log
@RequiredArgsConstructor
@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/main")
    public ChatMessage onMessage(ChatMessage message){
        return message.withTimestamp(Instant.now());
    }

}
