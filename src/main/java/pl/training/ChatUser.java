package pl.training;

import lombok.With;

public record ChatUser(String clientId, String privateClientId, String username, @With UserStatus status) {
}
