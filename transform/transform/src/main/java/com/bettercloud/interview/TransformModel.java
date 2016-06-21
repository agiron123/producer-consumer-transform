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

    public int addEntry(String email, int total) {
        int current;
        if (model.containsKey(email)) {
            current = model.get(email) + total;
            model.put(email, current + total);
        } else {
            current = total;
        }

        model.put(email, current);
        return current;
    }

    public ConcurrentHashMap<String, Integer> getModel() {
        return this.model;
    }
}