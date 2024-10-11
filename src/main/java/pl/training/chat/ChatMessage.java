package pl.training.chat;

import lombok.*;

import java.time.Instant;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private String sender;
    private Set<String> recipients;
    private String text;
    @With
    @Builder.Default
    private Instant timestamp = Instant.now();

    public boolean isBroadcast() {
        return recipients == null || recipients.isEmpty();
    }

}
