package com.bettercloud.interview;
import com.fasterxml.jackson.databind.JsonNode;

public interface ITransformer {
    public void transform(JsonNode rootNode);
}