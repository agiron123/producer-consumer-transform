package com.bettercloud.interview;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumeTask implements IConsumer{
    private IConsumerModel model;
    private JsonFactory jsonFactory;

    @Autowired
    public ConsumeTask(IConsumerModel model) {
        this.model = model;
        this.jsonFactory = new JsonFactory();
    }

    public void consume(JsonNode rootNode) {
        JsonNode logRoot = rootNode.path("logs");
        Iterator<JsonNode> iterator = logRoot.elements();
        while(iterator.hasNext()) {
            JsonNode current = iterator.next();
            String email = current.get("email").asText();
            model.addEntry(email);
        }
    }
}