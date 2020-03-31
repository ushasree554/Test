package com.molcon.escalex.microbiology.querybuilder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.pojo.SubObject;
import com.molcon.escalex.microbiology.querybuilder.MongoQueryBuilder;

public class MongoQueryBuilderTest {

	@InjectMocks
	MongoQueryBuilder mdd;
	
	
	@Before
	public void initializeMockito() {
		MockitoAnnotations.initMocks(this);
	}


   @Test
   public void textFilterCriteriaMethod() {
	   RequestQuery rQuery =  getQueryObject();
	   Criteria[] cr =  mdd.getFilterCriteria(rQuery);
	   assertEquals(1, cr.length);
	   
	   Query query = mdd.buildQuery(rQuery);
	   assertEquals(2, query.getLimit());
	   
	   query =  mdd.getLookUpQuery(rQuery);
	   assertEquals(2, query.getLimit());
	   
	   query =  mdd.getSearchResultQuery(rQuery);
	   assertEquals(2, query.getLimit());
	   
	   
   }

  
	public RequestQuery getQueryObject() {

		RequestQuery rQuery = new RequestQuery();
		SubObject sub = new SubObject();
		sub.setField("foodCategory");
		sub.setValue("milk");
		sub.setOrderBy("asc");

		rQuery.setTotalItems(2);

		List<SubObject> query = new ArrayList<>();
		query.add(sub);
		rQuery.setQuery(query);

		List<SubObject> filter = new ArrayList<>();
		filter.add(sub);
		rQuery.setFilter(filter);

		rQuery.setSort(sub);
		rQuery.setOffset(1);


		return rQuery;
	}
}
