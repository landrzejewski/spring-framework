package pl.training.shop.payments.adapters.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Scope("session")
@Service
public class SessionStore {

    private final Map<String, String> properties = new HashMap<>();

    public String get(Object key) {
        return properties.get(key);
    }

    public String put(String key, String value) {
        return properties.put(key, value);
    }

}
