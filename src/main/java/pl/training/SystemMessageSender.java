package pl.training;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemMessageSender {

    private static final String SYSTEM_SENDER = "System";

    private final SimpMessagingTemplate messagingTemplate;
    private final InMemoryChatUserRepository repository;

    @Value("${main-topic}")
    @Setter
    private String mainTopic;
    @Value("${user-list-topic}")
    @Setter
    private String userListTopic;

    public void sendToAll(String text) {
        var message = ChatMessage.builder()
                .sender(SYSTEM_SENDER)
                .text(text)
                .build();
        messagingTemplate.convertAndSend(mainTopic, message);
    }

    public void sendUserList() {
        var chatUsers = repository.getAll().stream()
                .filter(user -> user.status().isVisible())
                .toList();
        messagingTemplate.convertAndSend(userListTopic, chatUsers);
    }

}
