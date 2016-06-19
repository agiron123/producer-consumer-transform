package com.bettercloud.interview;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		//Note: Can also do this via application.properties file.
		System.getProperties().put( "server.port", 4001 );
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		System.out.println("Consumer loaded.");
	}
}