package pl.training.chat;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

@Repository
public class InMemoryChatUserRepository {

    private final Map<String, ChatUser> users = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String socketId, ChatUser user) {
        lock.writeLock().lock();
        users.put(socketId, user);
        lock.writeLock().unlock();
    }

    public Optional<ChatUser> get(String socketId) {
        lock.readLock().lock();
        var result = Optional.ofNullable(users.get(socketId));
        lock.readLock().unlock();
        return result;
    }

    public void remove(String socketId) {
        lock.writeLock().lock();
        users.remove(socketId);
        lock.writeLock().unlock();
    }

    public Collection<ChatUser> getAll() {
        lock.readLock().lock();
        var result = users.values();
        lock.readLock().unlock();
        return result;
    }

    public void updateStatus(String socketId, boolean isVisible) {
        lock.writeLock().lock();
        get(socketId).ifPresent(user -> put(socketId, user.withVisible(isVisible)));
        lock.writeLock().unlock();
    }

    public Stream<ChatUser> findByClientIds(Set<String> clientIds) {
        return getAll().stream().filter(chatUser -> clientIds.contains(chatUser.clientId()));
    }

}
