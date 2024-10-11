package pl.training.chat;

import lombok.extern.java.Log;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.time.Instant;
import java.util.Map;

@Log
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        attributes.put("connectionTimestamp", Instant.now());
        if (request instanceof ServletServerHttpRequest httpRequest) {
            log.info("Handshake request from: " + httpRequest.getURI());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if (request instanceof ServletServerHttpRequest httpRequest) {
            log.info("Handshake request accepted for: " + httpRequest.getURI());
        }
    }

}
