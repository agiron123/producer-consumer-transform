package com.bettercloud.interview;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

@RestController
public class ProducerController {
    private LogParser logParser;
    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    //TODO: Extra Credit: Use dependency injection to initialize the log parser.
    public ProducerController() {
        this.logParser = new LogParser(new File("C:\\Users\\Andre\\Desktop\\producer\\producer\\resources\\logs"));
    }

    @RequestMapping("/produce")
    public String produce() {
        return this.logParser.parse();
    }
}