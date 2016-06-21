package com.bettercloud.interview;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@RestController
public class ProducerController {
    private LogParser logParser;
    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public ProducerController(RestTemplate restTemplate) {
        String logDir = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logs";
        this.logParser = new LogParser(new File(logDir), restTemplate);
    }

    @RequestMapping("/produce")
    public String produce() {
        return this.logParser.parse();
    }
}