package com.bettercloud.interview;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonFactory;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
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

    private ITransformModel transformModel = mock(ITransformModel.class);
    private ITransformer transformer = mock(ITransformer.class);
    private JsonFactory jsonFactory;

    @Before
    public void setUp() {
        jsonFactory = new JsonFactory();
    }

    @Test
    public void postTransformValidJSON() {

    }

    @Test
    public void postTransformInvalidJSON() {

    }

}
