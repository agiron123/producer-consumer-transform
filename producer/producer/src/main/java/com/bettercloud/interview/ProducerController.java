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

    public ProducerController() {
        String logDir = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logs";
        this.logParser = new LogParser(new File(logDir));
    }

    @RequestMapping("/produce")
    public String produce() {
        return this.logParser.parse();
    }
}