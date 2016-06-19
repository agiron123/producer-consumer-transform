package com.bettercloud.interview;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

@RestController
public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    public ConsumerController() {

    }

    @RequestMapping("/consume")
    public String produce() {
        //TODO: Implement consumer logic here.
        return "[ConsumerController]: Consume route hit!";
    }
}