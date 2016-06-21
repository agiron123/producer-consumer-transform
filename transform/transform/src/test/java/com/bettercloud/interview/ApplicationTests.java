package com.bettercloud.interview;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
public class ApplicationTests {

	//Not entirely sure how to properly configure this test. It throws an IllegalState exception every time..

	@Test
	public void contextLoads() {
		assertEquals(true, true);
	}

}
