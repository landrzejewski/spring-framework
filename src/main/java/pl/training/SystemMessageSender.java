package pl.training;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemMessageSender {

    private static final String SENDER = "System";

    private final SimpMessagingTemplate messagingTemplate;
    private final InMemoryChatUserRepository repository;

    @Value("${main-topic}")
    @Setter
    private String mainTopic;

    public void sendToAll(String text) {
        var message = ChatMessage.builder()
                .sender(SENDER)
                .text(text)
                .build();
        messagingTemplate.convertAndSend(mainTopic, message);
    }

}
