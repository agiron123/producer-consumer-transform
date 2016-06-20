package com.bettercloud.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import org.springframework.http.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fasterxml.jackson.core.JsonParser;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

@RestController
public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    public IConsumerModel consumerModel;
    private JsonFactory jsonFactory;

    @Autowired
    public ConsumerController(IConsumerModel consumerModel) {
        //Note: we could simply do this with an in memory Concurrent Hashmap.
        //But to show OOP skills, we will use some Dependency Injection.
        this.consumerModel = consumerModel;

        this.jsonFactory = new JsonFactory();
    }

    //Here is how we can accept a POST request.
    @RequestMapping(value="/consume", method = RequestMethod.POST)
    public ResponseEntity<String> consume(@RequestBody String jsonString) {
        try {
            //Clear out the hashmap before parsing the request body.
            consumerModel.clear();

            JsonParser parser = jsonFactory.createJsonParser(jsonString);
            parser.setCodec(new ObjectMapper());
            JsonNode jsonNode = parser.readValueAsTree();

            //Spin up a new task to handle the processing of the message contents and pass in the ConcurrentHashMap.
            ConsumeTask consumeTask = new ConsumeTask(consumerModel);
            consumeTask.consume(jsonNode);

            //Send the tally data over to the Transform to get a global tally of email counts.
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode outputRoot = objectMapper.createObjectNode();
            JsonNode tallyNode = objectMapper.createArrayNode();
            ((ObjectNode)outputRoot).put("tally", tallyNode);

            //Print out the tally data.
            //TODO: Have getModel() return something more generic.
            ConcurrentHashMap<String, Integer> tallyMap = consumerModel.getModel();
            for (String email: tallyMap.keySet()) {
                Integer total = tallyMap.get(email);
                TallyEntry tallyEntry = new TallyEntry(email, total);
                ((ArrayNode)tallyNode).addPOJO(tallyEntry);
            }

            String jsonMap = objectMapper.writeValueAsString(outputRoot);
            System.out.println("JSON OUTPUT:");
            System.out.println(jsonMap);

        } catch (Exception e) {
            System.out.println("Consumer Controller exception caught!");
        }

        //Send a response to the producer to let it know that this data has been processsed.
        return new ResponseEntity<String>("Hello World", null, HttpStatus.OK);
    }
}