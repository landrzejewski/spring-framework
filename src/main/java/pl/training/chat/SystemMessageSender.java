package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemMessageSender {

    private static final String SYSTEM_SENDER = "System";

    private final SimpMessagingTemplate messagingTemplate;
    private final TimeProvider timeProvider;

    @Setter
    @Value("${main-room}")
    private String mainRoom;

    public void send(String text) {
        var message = ChatMessageDto.builder()
                .sender(SYSTEM_SENDER)
                .text(text)
                .timestamp(timeProvider.getFormattedTime())
                .build();
        messagingTemplate.convertAndSend(mainRoom, message);
    }

}
