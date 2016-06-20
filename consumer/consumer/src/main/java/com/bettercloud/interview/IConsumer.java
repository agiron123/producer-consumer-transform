package com.bettercloud.interview;
import com.fasterxml.jackson.databind.JsonNode;

public interface IConsumer {
    public void consume(JsonNode rootNode);
}