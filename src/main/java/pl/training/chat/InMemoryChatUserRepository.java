package pl.training.chat;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.synchronizedMap;

@Repository
public class InMemoryChatUserRepository {

    private final Map<String, ChatUser> users = synchronizedMap(new HashMap<>());

    public void put(String socketId, ChatUser user) {
        users.put(socketId, user);
    }

    public Optional<ChatUser> get(String socketId) {
        return Optional.ofNullable(users.get(socketId));
    }

    public void remove(String socketId) {
        users.remove(socketId);
    }

    public Collection<ChatUser> getAll() {
        return users.values();
    }

    public Stream<ChatUser> findByClientIds(Set<String> clientIds) {
        return getAll().stream().filter(chatUser -> clientIds.contains(chatUser.clientId()));
    }

}
