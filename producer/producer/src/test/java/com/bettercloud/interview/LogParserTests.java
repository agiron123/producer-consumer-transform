package com.bettercloud.interview;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by andre on 6/21/16.
 */
public class LogParserTests {

    private LogParser parser;
    private File logDir = mock(File.class);
    private File mockFile = mock(File.class);
    private File[] mockDir;
    private FileReader mockFileReader;
    private BufferedReader mockBufferedReader;

    private RestTemplate restTemplate = mock(RestTemplate.class);

    @Before
    public void setUp() {
        mockFileReader = mock(FileReader.class);
        mockBufferedReader = mock(BufferedReader.class);
    }

    @Test
    public void readFileShouldReadValidFilePath() throws IOException {
        //Mock the directory.
        File[] mockDir = new File[1];
        mockDir[0] = mockFile;
        when(logDir.listFiles()).thenReturn(mockDir);

        //Return some text
        LogParser parser = new LogParser(logDir, restTemplate);

        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine()).thenReturn("Hello World!", null);

        String fileContents = parser.readFile(bufferedReader);
        assertEquals(fileContents, "Hello World!");
    }

    @Test(expected = IOException.class)
    public void readFileShouldThrowIOException() throws IOException {
        BufferedReader nullReader = mock(BufferedReader.class);
        when(nullReader.readLine()).thenThrow(new IOException("LogParserTest IOException!"));
        LogParser parser = new LogParser(mockFile, restTemplate);
        parser.readFile(nullReader);
    }
}
