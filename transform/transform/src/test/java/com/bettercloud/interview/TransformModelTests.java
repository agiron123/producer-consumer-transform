package com.bettercloud.interview;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by andre on 6/21/16.
 */
public class TransformModelTests {

    private TransformModel model;

    @Before
    public void setup() {
        model = new TransformModel();
    }

    @Test
    public void shouldCreateEmptyModel() {
        assertEquals(model.getModel().size(), 0);
    }

    @Test
    public void shouldAddEntryToModel() {
        model.addEntry("email@email.com", 1);
        assertEquals(model.getModel().size(), 1);
    }

    @Test
    public void shouldAddTotalToExistingKey() {
        model.addEntry("email@email.com", 1);
        model.addEntry("email@email.com", 9000);

        ConcurrentHashMap<String, Integer> map = model.getModel();
        assertEquals((int)map.get("email@email.com"), 9001);
        assertEquals(map.size(), 1);
    }

    @Test
    public void distinctKeysShouldHaveTotal() {
        model.addEntry("email@email.com", 123);
        model.addEntry("myemail@email.com", 456);

        ConcurrentHashMap<String, Integer> map = model.getModel();
        assertEquals((int)map.get("email@email.com"), 123);
        assertEquals((int)map.get("myemail@email.com"), 456);
        assertEquals(map.size(), 2);
    }
}
