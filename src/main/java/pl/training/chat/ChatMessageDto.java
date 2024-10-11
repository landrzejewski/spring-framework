package pl.training.chat;

import lombok.*;

import java.time.Instant;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private String sender;
    private Set<String> recipients;
    private String text;
    @With
    @Builder.Default
    private Instant timestamp = Instant.now();

}
