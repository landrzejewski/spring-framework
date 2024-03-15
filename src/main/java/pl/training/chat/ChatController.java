package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log
public class ChatController {

    private final TimeProvider timeProvider;
    private final SimpMessagingTemplate messagingTemplate;

    @Setter
    @Value("${main-room}")
    private String mainRoom;
    @Setter
    @Value("${private-room-prefix}")
    private String privateRoomPrefix;

    @MessageMapping("/chat")
    public void onMessage(@Payload ChatMessageDto chatMessageDto,
                          @Header("simpSessionId") String socketId,
                          SimpMessageHeaderAccessor simpMessageHeaderAccessor
    ) {
        var attributes = simpMessageHeaderAccessor.getSessionAttributes();
        var username = attributes.get(socketId);
        // var clientId = attributes.get("clinetId");
        log.info("New message received: " + chatMessageDto);
        var timestamp = timeProvider.getFormattedTime();
        var response = chatMessageDto.withTimestamp(timestamp);
        if (chatMessageDto.isBroadcast()) {
            messagingTemplate.convertAndSend(mainRoom, response);
        } else {
            messagingTemplate.convertAndSend(privateRoomPrefix + username, response);
            chatMessageDto.getRecipients()
                    .forEach(recipient -> messagingTemplate.convertAndSend(privateRoomPrefix + recipient, response));
        }
    }

    /*@MessageMapping("/chat")
    @SendTo("/main-room")
    public ChatMessageDto onMessage(ChatMessageDto chatMessageDto) {
        log.info("New message received: " + chatMessageDto);
        var timestamp = timeProvider.getFormattedTime();
        return chatMessageDto.withTimestamp(timestamp);
    }*/

}
