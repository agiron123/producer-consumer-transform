package com.bettercloud.interview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RestTemplate restTemplate;

    public LogParser(File logDirectory, RestTemplate restTemplate) {
        this.logDirectory = logDirectory;
        this.jsonFactory = new JsonFactory();
        this.restTemplate = restTemplate;
    }

    public String parse() {
        String result = null;
        try {
            File[] fileList = logDirectory.listFiles();
            //Go through each JSON file and get the JSON contents from that file.
            for(int i = 0; i < fileList.length; i++) {
                File file = fileList[i];
                result = readFile(new BufferedReader(new FileReader(file)));

                //Send the data off to the consumer.
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> entity = new HttpEntity<>(result,headers);
                restTemplate.postForObject("http://localhost:4001/consume", entity, String.class);
            }
        } catch (IOException e) {
            logger.error("[ProducerController]: IOException caught while processing input file. Message: " + e.getMessage());
            result = null;
        }

        return result;
    }

    public String readFile(BufferedReader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        try {
            String line = reader.readLine();
            while (line != null) {
                // process the line.
                result.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw e;
        }
        return result.toString();
    }
}