package com.bettercloud.interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransformModel implements ITransformModel {
    private ConcurrentHashMap<String, Integer> model;

    public TransformModel() {
        this.model = new ConcurrentHashMap<String, Integer>();
    }

    public void addEntry(String email, int total) {
        //Really similar code to that of the consumer.
        //TODO: Move to a shared package.

        if (model.containsKey(email)) {
            int current = model.get(email);
            model.put(email, current + total);
        } else {
            model.put(email, 1);
        }
    }

    public ConcurrentHashMap<String, Integer> getModel() {
        return this.model;
    }
}