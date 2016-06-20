package com.bettercloud.interview;
import java.util.concurrent.ConcurrentHashMap;

public interface IConsumerModel {
    public void addEntry(String email);
    public void clear();
    public ConcurrentHashMap<String, Integer> getModel();
}