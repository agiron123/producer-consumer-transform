package com.bettercloud.interview;
import java.util.concurrent.ConcurrentHashMap;

public interface ITransformModel {
    int addEntry(String email, int total);
    ConcurrentHashMap<String, Integer> getModel();
}