package com.bettercloud.interview;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

@RestController
public class TransformController {
    private static final Logger logger = LoggerFactory.getLogger(TransformController.class);

    public TransformController() {

    }

    @RequestMapping("/transform")
    public String produce() {
        //TODO: Implement consumer logic here.
        return "[TransformController]: Transform route hit!";
    }
}