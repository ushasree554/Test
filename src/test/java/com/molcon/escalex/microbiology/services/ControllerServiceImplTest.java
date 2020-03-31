package com.molcon.escalex.microbiology.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.molcon.escalex.microbiology.mongodao.MongoDataDAO;
import com.molcon.escalex.microbiology.pojo.DocumentBin;
import com.molcon.escalex.microbiology.pojo.HeaderDocuments;
import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.services.ControllerServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerServiceImplTest {
	
	@Mock
	MongoDataDAO mongoDAO;

	@InjectMocks
	private ControllerServiceImpl  cs;
	
	@Before
	public void initializeMockito() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testHeaderData() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setTotalItems(10);
		h.setType("Test");
		h.setMembers(null);
		when(mongoDAO.getHeaders()).thenReturn(h);
		assertEquals(h.getTotalItems(), cs.getHeaders().getTotalItems());
	}
	
	@Test
	public void testAllHeaderData() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setTotalItems(10);
		h.setType("Test");
		h.setMembers(null);
		when(mongoDAO.getAllHeaders()).thenReturn(h);
		assertEquals(10, cs.getAllHeaders().getTotalItems());
	}
	
	@Test
	public void testLookupData() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setTotalItems(10);
		h.setType("Test");
		h.setMembers(null);
		RequestQuery query = new RequestQuery();
		when(mongoDAO.getLookUPData(any(RequestQuery.class))).thenReturn(h);
		assertEquals(10, cs.getLookUP(query).getTotalItems());
	}
	
	@Test
	public void testSearchData() throws Exception {
		DocumentBin doc = new DocumentBin();
		doc.setTotalItems(10);
		doc.setMembers(null);
		
		RequestQuery query = new RequestQuery();
		when(mongoDAO.getSearchData(any(RequestQuery.class))).thenReturn(doc);
		assertEquals(10, cs.getSearchResult(query).getTotalItems());
	}

	
}
