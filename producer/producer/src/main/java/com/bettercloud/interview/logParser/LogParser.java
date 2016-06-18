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

public class LogParser {
    private File logDirectory;
    private static final Logger logger = LoggerFactory.getLogger(LogParser.class);
    private JsonFactory jsonFactory;
    public LogParser(File logDirectory) {
        this.logDirectory = logDirectory;
        this.jsonFactory = new JsonFactory();
    }

    public String parse() {
        StringBuilder response = new StringBuilder();
        String result;
        try {
            File[] fileList = logDirectory.listFiles();
            //Go through each JSON file and get the JSON contents from that file.
            for(int i = 0; i < fileList.length; i++) {
                File file = fileList[i];
                JsonParser parser = jsonFactory.createJsonParser(file);
                parser.setCodec(new ObjectMapper());
                JsonNode jsonNode = parser.readValueAsTree();
                response.append(readJsonData(jsonNode));
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