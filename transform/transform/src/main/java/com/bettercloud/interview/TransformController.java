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

import com.fasterxml.jackson.core.JsonParser;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


@RestController
public class TransformController {
    private static final Logger logger = LoggerFactory.getLogger(TransformController.class);

    private ITransformModel transformModel;
    private ITransformer transformer;
    private JsonFactory jsonFactory;

    @Autowired
    public TransformController(ITransformModel transformModel, ITransformer transformer) {
        this.transformModel = transformModel;
        this.transformer = transformer;
        this.jsonFactory = new JsonFactory();
    }

    @RequestMapping(value="/transform", method = RequestMethod.POST)
    public ResponseEntity<String> transform(@RequestBody String requestBody) {
        try {
            //Get root JSON node
            JsonParser parser = jsonFactory.createParser(requestBody);
            parser.setCodec(new ObjectMapper());
            JsonNode rootJsonNode = parser.readValueAsTree();

            //Send root JSON node to the transformer to process.
            transformer.transform(rootJsonNode);

            //Put contents of the hashmap into JSON.
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode outputRoot = objectMapper.createObjectNode();
            JsonNode tallyNode = objectMapper.createArrayNode();
            ((ObjectNode)outputRoot).put("tally", tallyNode);

            ConcurrentHashMap<String, Integer> tallyMap = transformModel.getModel();
            for (String email: tallyMap.keySet()) {
                Integer total = tallyMap.get(email);
                TallyEntry tallyEntry = new TallyEntry(email, total);
                ((ArrayNode)tallyNode).addPOJO(tallyEntry);
            }

            //Print the updated tally to the console.
            String jsonMap = objectMapper.writeValueAsString(outputRoot);
            System.out.println("Updated Tally:");
            System.out.println(jsonMap);

        } catch (Exception e) {
            System.out.println("[TransformController]: Exception caught in transform route.");
        }

        return new ResponseEntity<>("[TransformController]: Transform route hit!", null, HttpStatus.OK);
    }

}