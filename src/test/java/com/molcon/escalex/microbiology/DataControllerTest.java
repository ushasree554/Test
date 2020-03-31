package com.molcon.escalex.microbiology;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.molcon.escalex.microbiology.DataController;
import com.molcon.escalex.microbiology.exception.RestExceptionHandler;
import com.molcon.escalex.microbiology.pojo.DocumentBin;
import com.molcon.escalex.microbiology.pojo.HeaderDocuments;
import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.services.ControllerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthorizationServerApplication.class)
public class DataControllerTest {

	private MockMvc mockMvc;

	@Mock
	ControllerService service;

	@InjectMocks
	DataController dc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(dc)
				.setControllerAdvice(new RestExceptionHandler())
				.build();
	}

	@Test
	public void testPingAPI() throws Exception {
		mockMvc.perform(get("/api/ping"))
		.andExpect(status().isOk())
		.andExpect(content().string("API IS UP AND RUNNING SUCCESSFULLY"));
	}

	@Test
	public void checkExceptions() throws Exception {

		when(dc.getHeader()).thenThrow(new RuntimeException("Unexpected Exception"));

		mockMvc.perform(get("/api/header"))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("Unexpected Exception"));
	}

	@Test
	public void testHeaderAPI() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setTotalItems(10);
		h.setType("Test");
		h.setMembers(null);
		when(service.getHeaders()).thenReturn(h);
		mockMvc.perform(get("/api/header").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalItems").value(10));
	}

	@Test
	public void testAllHeaderAPI() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setTotalItems(10);
		h.setType("Test");
		h.setMembers(null);
		when(service.getAllHeaders()).thenReturn(h);	
		mockMvc.perform(get("/api/all-header").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalItems").value(10));
	}

	@Test
	public void testLookupAPI() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setTotalItems(10);
		h.setType("Test");
		h.setMembers(null);
		String queryJson = "{  \"totalItems\": 2,  \"query\": [    {      \"field\": \"foodCategory\",      \"value\": \"milk\"    }  ],  \"filter\": [    {      \"field\": \"criteria\",      \"value\": \"Food Safety\"    }  ],  \"offset\": 1,  \"sort\": {    \"field\": \"foodCategory\",    \"orderBy\": \"asc\"  }}";
		when(service.getLookUP(any(RequestQuery.class))).thenReturn(h);	
		mockMvc.perform(post("/api/lookup")
				.accept(MediaType.APPLICATION_JSON)
				.content(queryJson)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalItems").value(10));
	}

	@Test
	public void testSearchAPI() throws Exception {

		DocumentBin doc = new DocumentBin();
		doc.setTotalItems(10);
		doc.setMembers(null);

		String queryJson = "{  \"totalItems\": 2,  \"query\": [    {      \"field\": \"foodCategory\",      \"value\": \"milk\"    }  ],  \"filter\": [    {      \"field\": \"criteria\",      \"value\": \"Food Safety\"    }  ],  \"offset\": 1,  \"sort\": {    \"field\": \"foodCategory\",    \"orderBy\": \"asc\"  }}";
		when(service.getSearchResult(any(RequestQuery.class))).thenReturn(doc);	
		mockMvc.perform(post("/api/search")
				.accept(MediaType.APPLICATION_JSON)
				.content(queryJson)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalItems").value(10));
	}
}
