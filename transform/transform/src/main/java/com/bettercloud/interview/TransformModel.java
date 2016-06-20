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
        if (model.containsKey(email)) {
            int current = model.get(email);
            model.put(email, current + total);
        } else {
            model.put(email, total);
        }
    }

    //TODO: Create an IStorage interface that we can use to make our implementation not dependant on hashmaps.
    //One add method with just email parameter, another one with a total to add. This would accommodate consumer and transformer.
    public ConcurrentHashMap<String, Integer> getModel() {
        return this.model;
    }
}