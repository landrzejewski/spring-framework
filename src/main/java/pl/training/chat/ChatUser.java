package pl.training.chat;

import lombok.With;

public record ChatUser(String clientId, String privateClientId, String username, @With boolean isVisible) {
}
