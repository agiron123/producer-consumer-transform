package com.bettercloud.interview;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ConcurrentHashMapConsumerModel implements IConsumerModel{
    private ConcurrentHashMap<String, Integer> map;
    public ConcurrentHashMapConsumerModel() {
        this.map = new ConcurrentHashMap<String, Integer>();
    }

    public ConcurrentHashMap<String, Integer> getModel() {
        return this.map;
    }

    public void addEntry(String email) {
        if (map.containsKey(email)) {
            int current = map.get(email);
            map.put(email, current + 1);
        } else {
            map.put(email, 1);
        }
    }

    public void clear() {
        map.clear();
    }
}