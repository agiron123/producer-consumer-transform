package com.bettercloud.interview;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Iterator;

@Component
public class Transformer implements ITransformer {
    private ITransformModel transformModel;

    @Autowired
    public Transformer(ITransformModel transformModel) {
        this.transformModel = transformModel;
    }
    public void transform(JsonNode rootNode) {
        //TODO: Make a shhared storage package and have a ConcurrentHashMapStorage implementation.
        //Also have an IStorage interface, to make our code cleaner.
        //Traverse through the nodes in the JSON object and add them to the model.

        JsonNode logRoot = rootNode.path("tally");
        Iterator elements = rootNode.elements();
        Iterator<JsonNode> iterator = logRoot.elements();

        while(iterator.hasNext()) {
            JsonNode current = iterator.next();
            String email = current.get("email").asText();
            int total = current.get("total").asInt();
            transformModel.addEntry(email, total);
        }
    }
}