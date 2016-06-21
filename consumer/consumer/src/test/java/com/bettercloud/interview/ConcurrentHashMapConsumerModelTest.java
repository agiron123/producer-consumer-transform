package com.bettercloud.interview;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class ConcurrentHashMapConsumerModelTest {
    private ConcurrentHashMapConsumerModel model;

    @Before
    public void initialize() {
        model = new ConcurrentHashMapConsumerModel();
    }

    @Test
    public void shouldAddNewEntry() {
        assertEquals(model.addEntry("email@email.com"), 1);
    }

    @Test
    public void shouldAddOneToDuplicateEntry() {
        model.addEntry("email@email.com");
        int total = model.addEntry("email@email.com");
        assertEquals(2, total);
    }

    @Test
    public void shouldAddNonDuplicateEntries() {
        assertEquals(model.addEntry("email@email.com"), 1);
        assertEquals(model.addEntry("anotherEmail@email.com"), 1);
    }

    @Test
    public void clearShouldEmptyHashMap() {
        model.addEntry("email@email.com");
        assertEquals(model.getModel().size(), 1);
        model.clear();
        assertEquals(model.getModel().size(), 0);
    }

}
