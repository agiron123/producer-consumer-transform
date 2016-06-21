package com.bettercloud.interview;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.JsonParser;

import java.util.Iterator;

public class ConsumeTaskTests {
    private static final Logger logger = LoggerFactory.getLogger(ConsumeTaskTests.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final JsonFactory jsonFactory = new JsonFactory();
    private IConsumerModel mockModel;

    @Before
    public void initialize() {
        mockModel = mock(IConsumerModel.class);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorNullArgumentTest() {
        ConsumeTask task = new ConsumeTask(null);
    }

    @Test
    public void constructorTest() {
        ConsumeTask task = new ConsumeTask(mockModel);
        assertNotNull(task);
    }

    @Test
    public void consumeTestNonNullCase() {
        //Arrange
        //Mock Json with log information.
        ConsumeTask task = new ConsumeTask(mockModel);

        String mockJson = "{\"logs\":[{\"id\":\"89004ef9-e825-4547-a83a-c9e9429e8f95\",\"email\":\"noah.sanchez@me.com\",\"message\":\"successfully handled skipped operation.\"}]}";
        try {
            JsonParser parser = jsonFactory.createParser(mockJson);
            parser.setCodec(new ObjectMapper());
            JsonNode jsonNode = parser.readValueAsTree();

            //Act
            //Consume should return the root json node. There is only one in our mock, so we know it should be the root.
            JsonNode output = task.consume(jsonNode);
            //Need to get the first element from the array
            Iterator<JsonNode> iterator = output.elements();
            JsonNode root = iterator.next();

            //Assert
            String email = root.get("email").asText();
            assertEquals(email, "noah.sanchez@me.com");

            String id = root.get("id").asText();
            assertEquals(id, "89004ef9-e825-4547-a83a-c9e9429e8f95");

            String message = root.get("message").asText();
            assertEquals(message, "successfully handled skipped operation.");
        } catch(Exception ex) {
            fail("Exception in consumeTestNonNullCase. Message: " + ex.getMessage());
        }
    }

    @Test(expected=NullPointerException.class)
    public void consumeTestNullJSON() {
        IConsumerModel mockModel = mock(IConsumerModel.class);
        ConsumeTask task = new ConsumeTask(mockModel);
        task.consume(null);
    }
}
