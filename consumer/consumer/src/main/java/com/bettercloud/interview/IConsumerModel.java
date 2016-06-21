package com.bettercloud.interview;
import java.util.concurrent.ConcurrentHashMap;

public interface IConsumerModel {
    int addEntry(String email);
    void clear();
    ConcurrentHashMap<String, Integer> getModel();
}