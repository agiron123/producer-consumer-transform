package com.bettercloud.interview;
import static org.mockito.Mockito.*;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.ConcurrentHashMap;

public class ConsumeControllerTests {
    private final IConsumerModel mockModel = mock(IConsumerModel.class);
    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new ConsumerController(mockModel)).build();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new ConsumerController(mockModel)).build();
    }

    @Test
    public void postConsume() throws Exception {
        //Arrange
        ConcurrentHashMap<String, Integer> mockMap = new ConcurrentHashMap<String,Integer>();
        mockMap.put("noah.sanchez@me.com", 1);
        when(mockModel.getModel()).thenReturn(mockMap);

        //Act and Assert
        String mockJSON = "{\"logs\":[{\"id\":\"89004ef9-e825-4547-a83a-c9e9429e8f95\",\"email\":\"noah.sanchez@me.com\",\"message\":\"successfully handled skipped operation.\"}]}";
        mvc.perform(MockMvcRequestBuilders.post("/consume").accept(MediaType.APPLICATION_JSON)
                .content(mockJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Status: done.")));
    }

    @Test
    public void postConsumeInvalidJSON() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/consume").accept(MediaType.APPLICATION_JSON)
                .content("asdf"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("Status: error processing request.")));
    }
}