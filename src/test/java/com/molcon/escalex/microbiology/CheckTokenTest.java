/*package com.molcon.escalex.microbiology;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.molcon.escalex.microbiology.services.LoginService;

public class CheckTokenTest {

	@Mock
	RestTemplate restTemplate;
	
	@InjectMocks
	CheckToken ckToken;
	@InjectMocks
	LoginService loginService; 
	
	@Before
	public void initializeMockito() {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	public void checkClientMethod() throws Exception {
		when(restTemplate.exchange(null, HttpMethod.POST, null, Object.class)).thenReturn(null);
		boolean flag = loginService.checkToken("abc");
		 assertEquals(true,flag);
	}
}
*/