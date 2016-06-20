package com.bettercloud.interview;
import java.util.concurrent.ConcurrentHashMap;

public interface ITransformModel {
    public void addEntry(String email, int total);
    public ConcurrentHashMap<String, Integer> getModel();
}