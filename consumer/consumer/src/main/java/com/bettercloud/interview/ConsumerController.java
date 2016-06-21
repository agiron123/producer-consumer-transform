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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.core.JsonParser;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    public IConsumerModel consumerModel;
    private JsonFactory jsonFactory;
    private RestTemplate restTemplate;

    @Autowired
    public ConsumerController(IConsumerModel consumerModel) {
        //Note: we could simply do this with an in memory Concurrent Hashmap.
        //But to show OOP skills, we will use some Dependency Injection.
        this.consumerModel = consumerModel;
        this.jsonFactory = new JsonFactory();
        this.restTemplate = new RestTemplate();
    }

    public ConsumerController() {
        System.out.println("Default constructor hit!");
    }

    //Here is how we can accept a POST request.
    @RequestMapping(value="/consume", method = RequestMethod.POST)
    public ResponseEntity<String> consume(@RequestBody String jsonString) {

        ResponseEntity<String> responseEntity = new ResponseEntity<String>("Status: error processing request.", null, HttpStatus.INTERNAL_SERVER_ERROR);

        try {
            if (jsonString == null){
                throw new IllegalArgumentException("parameter jsonString is null.");
            }
            //Clear out consumer model before parsing the Json body
            consumerModel.clear();

            JsonParser parser = jsonFactory.createParser(jsonString);
            parser.setCodec(new ObjectMapper());
            JsonNode jsonNode = parser.readValueAsTree();

            //Spin up a new task to handle the processing of the message contents and pass in the ConcurrentHashMap.
            ConsumeTask consumeTask = new ConsumeTask(consumerModel);
            JsonNode consumeRoot = consumeTask.consume(jsonNode);

            if (consumeRoot == null) {
                throw new NullPointerException("consume task returned null.");
            }

            //Send the tally data over to the Transform to get a global tally of email counts.
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode outputRoot = objectMapper.createObjectNode();
            JsonNode tallyNode = objectMapper.createArrayNode();
            ((ObjectNode)outputRoot).set("tally", tallyNode);

            //Print out the tally data.
            ConcurrentHashMap<String, Integer> tallyMap = consumerModel.getModel();
            for (String email: tallyMap.keySet()) {
                Integer total = tallyMap.get(email);
                TallyEntry tallyEntry = new TallyEntry(email, total);
                ((ArrayNode)tallyNode).addPOJO(tallyEntry);
            }

            String jsonMap = objectMapper.writeValueAsString(outputRoot);
            System.out.println("Tally:");
            System.out.println(jsonMap);

            //Send the data off to the transform.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //Future Work: Tell producer to try again with the tally.
            HttpEntity<String> entity = new HttpEntity<>(jsonMap,headers);
            restTemplate.postForObject("http://localhost:4002/transform", entity, String.class);

        } catch (Exception e) {
            logger.error("[ConsumerController.consume()]: " + e.getMessage());
            return responseEntity;
        }

        //Send a response to the producer to let it know that this data has been processsed.
        responseEntity = new ResponseEntity<>("Status: done.", null, HttpStatus.OK);
        return responseEntity;
    }
}