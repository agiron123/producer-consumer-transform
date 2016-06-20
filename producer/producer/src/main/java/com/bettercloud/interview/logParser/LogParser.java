package com.bettercloud.interview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.client.RestTemplate;

public class LogParser {
    private File logDirectory;
    private static final Logger logger = LoggerFactory.getLogger(LogParser.class);
    private JsonFactory jsonFactory;
    private ClientHttpRequestFactory requestFactory;
    private RestTemplate restTemplate;

    public LogParser(File logDirectory) {
        this.logDirectory = logDirectory;
        this.jsonFactory = new JsonFactory();
        this.restTemplate = new RestTemplate();
    }

    public String parse() {
        //TODO: Rename to jsonContents.
        StringBuilder response = new StringBuilder();
        String result;
        BufferedReader reader;
        try {
            File[] fileList = logDirectory.listFiles();
            //Go through each JSON file and get the JSON contents from that file.
            for(int i = 0; i < fileList.length; i++) {
                //Reset the String builder
                response.setLength(0);

                //Get JSON data from each file.
                File file = fileList[i];
                reader = new BufferedReader(new FileReader(file));
                try {
                    String line = reader.readLine();
                    while (line != null) {
                        System.out.println("Line contents: " + line);

                        // process the line.
                        response.append(line);
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    System.out.println("[LogParser].parse() IO exception caught");
                }

                //Send the data off to the consumer.
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> entity = new HttpEntity<String>(response.toString(),headers);
                String consumeResponse = restTemplate.postForObject("http://localhost:4001/consume", entity, String.class);
                System.out.println("Response from consume POST request: " + consumeResponse);
            }

            result = response.toString();
        } catch (IOException e) {
            //TODO: Use appropriate response code and exception handling.
            //Properly form response oject
            response.append("Exception caught!");
            result = "Exception caught";
        }

        return result;
    }

    private String readJsonData(JsonNode node) {
        StringBuilder builder = new StringBuilder();
        JsonNode rootNode = node.path("logs");
        Iterator<JsonNode> iterator = rootNode.elements();
        while(iterator.hasNext()) {
            //TODO: Extra credit: Figure out how to get this to stream to the consumer.
            String current = iterator.next().toString() + ",";
            builder.append(current);
        }
        return builder.toString();
    }
}