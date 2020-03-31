package com.molcon.escalex.microbiology.mongodao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.molcon.escalex.microbiology.mongodao.MongoDataDAO;
import com.molcon.escalex.microbiology.pojo.DocumentBin;
import com.molcon.escalex.microbiology.pojo.HeaderDocuments;
import com.molcon.escalex.microbiology.pojo.Member;
import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.querybuilder.MongoQueryBuilder;
import com.molcon.escalex.microbiology.services.HeaderRepository;
import com.mongodb.BasicDBObject;

public class MongoDataDAOTest {

	@Mock
	MongoQueryBuilder mgQueryBuild;
	
	@Mock
	HeaderRepository headerRepository;
	
	@Mock
	MongoTemplate mongoTemplate;
	
	@InjectMocks
	MongoDataDAO mdd;
	
	
	@Before
	public void initializeMockito() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testHeaderData() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setType("Test");
		Member m = new Member();
		m.setField("foodCategory");
		m.setName("FOOD CATEGORY");
		m.setValue("milk");
		List<Member> ls = new ArrayList<>();
		ls.add(m);
		h.setMembers(ls);
		when(headerRepository.findByType("primaryHeader")).thenReturn(h);
		assertEquals(1, mdd.getHeaders().getTotalItems());
	}
	
	@Test
	public void testAllHeaderData() throws Exception {
		HeaderDocuments h = new HeaderDocuments();
		h.setType("Test");
		Member m = new Member();
		m.setField("foodCategory");
		m.setName("FOOD CATEGORY");
		m.setValue("milk");
		List<Member> ls = new ArrayList<>();
		ls.add(m);
		h.setMembers(ls);
		when(headerRepository.findByType("allHeader")).thenReturn(h);
		HeaderDocuments result = mdd.getAllHeaders();
		List<Member> resultList = result.getMembers();
		assertEquals(1, result.getTotalItems());
		assertEquals("milk", resultList.get(0).getValue());
		assertEquals("FOOD CATEGORY", resultList.get(0).getName());
		assertEquals("foodCategory", resultList.get(0).getField());
	}
	
	@Test
	public void testLookupData() throws Exception {
		
		RequestQuery rQuery = new RequestQuery();
		when(mgQueryBuild.getLookUpQuery(rQuery)).thenReturn(null);
		when(mongoTemplate.count(null, BasicDBObject.class, null)).thenReturn(1l);
		when(mongoTemplate.find(null, BasicDBObject.class, null)).thenReturn(null);
		HeaderDocuments result = mdd.getLookUPData(rQuery);
		List<Member> resultList = result.getMembers();
		assertEquals(0, result.getTotalItems());
		assertEquals(0, resultList.size());
	}
	
	@Test
	public void testObjectIteration() {
		List<BasicDBObject> obj = new ArrayList<>();
		BasicDBObject j = new BasicDBObject();
		j.append("foodCategory", "milk");
		obj.add(j);	
		
		BasicDBObject jj = new BasicDBObject();
		jj.append("foodCategory1", "milk1");
		obj.add(jj);	
		
		List<Member> members = mdd.getMemberList(obj);
		assertEquals("milk", members.get(0).getValue());
		assertEquals("foodCategory", members.get(0).getField());
		
	}
	
	/*@Test
	public void testSearchData() throws Exception {
		
		RequestQuery rQuery = new RequestQuery();
		when(mgQueryBuild.getSearchResultQuery(rQuery)).thenReturn(null);
		when(mongoTemplate.count(null, BasicDBObject.class, null)).thenReturn(1l);
		when(mongoTemplate.find(null, BasicDBObject.class, null)).thenReturn(null);
		DocumentBin result = mdd.getSearchData(rQuery);
		List<List<Member>> resultList = result.getMembers();
		assertEquals(0, result.getTotalItems());
		assertEquals(0, resultList.size());
	}*/
	
	@Test
	public void testSearchObjectIteration() throws JSONException {
		List<BasicDBObject> obj = new ArrayList<>();
		BasicDBObject j = new BasicDBObject();
		j.append("foodCategory", "milk");
		obj.add(j);	
		
		BasicDBObject jj = new BasicDBObject();
		jj.append("foodCategory1", "milk1");
		obj.add(jj);	
		
		List<List<Member>> members = mdd.getSearchMemberList(obj);
		assertEquals("milk", members.get(0).get(0).getValue());
		assertEquals("foodCategory", members.get(0).get(0).getField());
		
	}
	

	
}
