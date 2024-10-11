package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SystemMessageSender {

    private static final String SYSTEM_SENDER = "System";

    @Value("${main-topic}")
    @Setter
    private String mainTopic;
    @Value("${user-list-topic}")
    @Setter
    private String userListTopic;
    @Value("${update-contacts-delay-in-mills}")
    @Setter
    private long updateDelay;

    private final SimpMessagingTemplate messagingTemplate;
    private final InMemoryChatUserRepository userRepository;
    private final TaskScheduler taskScheduler;

    public void sendToAll(String text) {
        var message = ChatMessage.builder()
                .sender(SYSTEM_SENDER)
                .text(text)
                .build();
        messagingTemplate.convertAndSend(mainTopic, message);
    }

    public void updateUserList() {
        var chatUsers = userRepository.getAll()
                .stream().filter(ChatUser::isVisible);
        var executionTime = Instant.now().plusMillis(updateDelay);
        taskScheduler.schedule(() -> messagingTemplate.convertAndSend(userListTopic, chatUsers), executionTime);
    }

}
