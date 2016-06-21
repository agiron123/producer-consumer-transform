package com.bettercloud.interview;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonFactory;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andre on 6/21/16.
 */
public class TransformControllerTests {

    private ITransformModel mockModel = mock(ITransformModel.class);
    private ITransformer mockTransformer = mock(ITransformer.class);
    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new TransformController(mockModel, mockTransformer)).build();

    @Before
    public void setUp() {

    }

    @Test
    public void postTransformValidJSON() throws Exception {
        String mockJSON = "{\"tally\":[{\"email\":\"ava.davis@gmail.com\"," +
                "\"total\":13},{\"email\":\"alyssa.jones@bettercloud.com\",\"total\":15}," +
                "{\"email\":\"alyssa.jones@bettercloud.com\",\"total\":10}," +
                "{\"email\":\"isabella.jackson@bettercloud.com\",\"total\":5}" +
                "]}\n";

        ConcurrentHashMap<String, Integer> mockMap = new ConcurrentHashMap<String, Integer>();
        mockMap.put("ava.davis@gmail.com", 13);
        mockMap.put("alyssa.jones@bettercloud.com", 25);
        mockMap.put("isabella.jackson@bettercloud.com", 5);

        when(mockModel.getModel()).thenReturn(mockMap);
        mvc.perform(MockMvcRequestBuilders.post("/transform").accept(MediaType.APPLICATION_JSON)
                .content(mockJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[TransformController]: Transform route hit!")));
    }

    @Test
    public void postTransformInvalidJSON() throws Exception {
        String mockJSON = "asdkjlasdf";

        mvc.perform(MockMvcRequestBuilders.post("/transform").accept(MediaType.APPLICATION_JSON)
                .content(mockJSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("[TransformController]: Error processing transform")));
    }

}
