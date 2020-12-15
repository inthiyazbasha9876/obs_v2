
package com.obs.employeeCertificationDetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(MockitoJUnitRunner.Silent.class)

@SpringBootTest()
public class EmployeeCerificationDetailsApplicationTests {
	protected MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	public void contextLoads() {
	}

}
