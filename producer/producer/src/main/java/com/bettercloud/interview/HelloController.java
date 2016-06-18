package com.bettercloud.interview;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/myRoute")
    public String myRoute() {
        return "Hello World, this is my Route!";
    }
}