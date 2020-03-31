package com.molcon.escalex.microbiology.mongodao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.molcon.escalex.microbiology.pojo.DocumentBin;
import com.molcon.escalex.microbiology.pojo.HeaderDocuments;
import com.molcon.escalex.microbiology.pojo.Member;
import com.molcon.escalex.microbiology.pojo.MetaDataBin;
import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.querybuilder.MongoQueryBuilder;
import com.molcon.escalex.microbiology.services.HeaderRepository;
import com.mongodb.BasicDBObject;

@Component
public class MongoDataDAO {

	private static final String DATA_COLLECTION = "microbiology_data";
	private static final String AUTO_COLLECTION = "autosuggest";
	
	
	@Autowired
	MongoQueryBuilder mgQueryBuild;

	@Autowired
	private HeaderRepository headerRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static Map<String, String> headDoc  = new LinkedHashMap<>();
	
	private static Set<String> primaryHeadList = new HashSet<>();
	
	@PostConstruct
	public void init() {	
		headDoc = new LinkedHashMap<>();
		HeaderDocuments headerDocuments = getAllHeaders();
		List<Member> memberList = headerDocuments.getMembers();
		for(Member m : memberList) {
			headDoc.put(m.getField(), m.getName());
		}
		primaryHeadList.add("foodCategory");
		primaryHeadList.add("microOrganism");
		primaryHeadList.add("level");
		primaryHeadList.add("examples");
		primaryHeadList.add("specificsType");
		primaryHeadList.add("gmp");
		
	  }
	
	public static Map<String, String> getListOfHeader() {
		return headDoc;
	}
	
	
	public HeaderDocuments getAllHeaders() {
		HeaderDocuments h = headerRepository.findByType("allHeader");
		h.setTotalItems(h.getMembers().size());
		return h;
	}

	public HeaderDocuments getHeaders() {
		HeaderDocuments headerDocuments = headerRepository.findByType("primaryHeader");
		List<Member> memberList = headerDocuments.getMembers();
		for(Member m : memberList) {
			m.setName(getListOfHeader().get(m.getField()));
		}
		headerDocuments.setTotalItems(memberList.size());
		return headerDocuments;
	}



	public HeaderDocuments getLookUPData(RequestQuery requestQuery){		
		Query mQuery = mgQueryBuild.getLookUpQuery(requestQuery);	
		HeaderDocuments h = new HeaderDocuments();
		long count = mongoTemplate.count(mQuery, BasicDBObject.class,AUTO_COLLECTION);
		h.setTotalItems(count);	
		List<BasicDBObject> obj = mongoTemplate.find(mQuery, BasicDBObject.class, AUTO_COLLECTION);
		h.setMembers(getMemberList(obj));
		return h;
	}

	public List<Member> getMemberList(List<BasicDBObject> obj) {
		List<Member> lm = new ArrayList<>();
		for(BasicDBObject j : obj) {
			for(String id : j.keySet()) {
				Member m = new Member();
				m.setField(id);
				m.setValue(j.getString(id));
				m.setName(getListOfHeader().get(id));
				lm.add(m);
			}	
		}
		return lm;
	}

	public DocumentBin getSearchData(RequestQuery requestQuery) {
		Query mQuery = mgQueryBuild.getSearchResultQuery(requestQuery);	
		System.out.println(mQuery.toString());
		DocumentBin h = new DocumentBin();
		long count = mongoTemplate.count(mQuery, BasicDBObject.class,DATA_COLLECTION);
		h.setTotalItems(count);	
		List<BasicDBObject> obj = mongoTemplate.find(mQuery, BasicDBObject.class, DATA_COLLECTION);
		h.setMembers(getSearchMemberList(obj));
		return h;
	}
	
	public List<List<Member>> getSearchMemberList(List<BasicDBObject> obj) {
		boolean isLevelPresent = false;
		List<List<Member>> lmm = new ArrayList<>();
		for(BasicDBObject j : obj) {
			List<Member> lm = new ArrayList<>();
			for(String id : j.keySet()) {
				if (id.equals("level")) {
					isLevelPresent = true;
				}
				Member m = new Member();
				m.setField(id);
				m.setValue(j.getString(id));
				m.setName(getListOfHeader().get(id));
				MetaDataBin mb = new MetaDataBin();
				if (primaryHeadList.contains(id)) {
					mb.setTableDisplay(false);
				} else {
					mb.setTableDisplay(true);
				}
				m.setMetaData(mb);
				lm.add(m);
			}
			if(!isLevelPresent) {
				Member m = new Member();
				m.setField("level");
				m.setValue("");
				m.setName("");
				MetaDataBin mb = new MetaDataBin();
				mb.setTableDisplay(false);
				lm.add(m);
			}
			lmm.add(lm);
			isLevelPresent = false;
		}
		return lmm;
	}



}
