package com.bettercloud.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import org.springframework.http.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fasterxml.jackson.core.JsonParser;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

@RestController
public class TransformController {
    private static final Logger logger = LoggerFactory.getLogger(TransformController.class);

    private ITransformModel transformModel;
    private ITransformer transformer;

    @Autowired
    public TransformController(ITransformModel transformModel, ITransformer transformer) {
        this.transformModel = transformModel;
        this.transformer = transformer;
    }

    @RequestMapping(value="/transform", method = RequestMethod.POST)
    public ResponseEntity<String> consume(@RequestBody String requestBody) {
        System.out.println("RequestBody:");
        System.out.println(requestBody);

        return new ResponseEntity<String>("[TransformController]: Transform route hit!", null, HttpStatus.OK);
    }

}