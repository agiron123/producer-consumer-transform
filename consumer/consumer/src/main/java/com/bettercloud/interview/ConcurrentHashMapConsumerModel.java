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

    //Returns the total for the current email entry after adding to model.
    public int addEntry(String email) {
        int current;
        if (map.containsKey(email)) {
            current = map.get(email) + 1;
        } else {
            current = 1;
        }

        map.put(email, current);
        return current;
    }

    public void clear() {
        map.clear();
    }
}