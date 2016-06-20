package com.bettercloud.interview;

public class TallyEntry {
    private String email;
    private int total;

    public TallyEntry(String email, int total) {
        this.email = email;
        this.total = total;
    }

    public String getEmail() {
        return this.email;
    }

    public int getTotal() {
        return this.total;
    }
}