package com.molcon.escalex.microbiology.querybuilder;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.pojo.SubObject;
import com.molcon.escalex.microbiology.util.StringUtil;

@Component
public class MongoQueryBuilder {
	private static final String REGEX_ANY = ".*";
	
	public Criteria[] getFilterCriteria(RequestQuery requestQuery) {
		List<SubObject>  query = requestQuery.getFilter();
	    Criteria[] criteriaFilter = new Criteria[query.size()];
	    for(int i=0; i< query.size(); i++) {
	    	criteriaFilter[i] = Criteria.where(
					query.get(i).getField()).is(query.get(i).getValue());
		}
	    return criteriaFilter;
	}
	
	public Query getSearchResultQuery(RequestQuery requestQuery) {
		Query mQuery = buildQuery(requestQuery);
		int queryMainSize = 0;
		int queryFilterSize=0;
		List<SubObject> queryMain = null;
		List<SubObject>  queryFilter = null;
		
		if (null!=requestQuery.getQuery() && !requestQuery.getQuery().isEmpty()) {
			queryMain = requestQuery.getQuery();
        	queryMainSize = queryMain.size();
		}
		if (null!=requestQuery.getFilter() && !requestQuery.getFilter().isEmpty()) {
		    queryFilter = requestQuery.getFilter();
			queryFilterSize = queryFilter.size();
		}
	
		Criteria[] criteriaFinal  = new Criteria[queryMainSize+queryFilterSize];
		int k=0;
		if (null!=queryMain) {
    		for(int i=k; i< queryMainSize; i++) {
    			String name = StringUtil.updateRegex(queryMain.get(i).getValue());
    			criteriaFinal[i] = Criteria.where(
    					queryMain.get(i).getField()).regex(REGEX_ANY+name+REGEX_ANY, "i");
    			k++;
    		}      	
		}
	
		if (null!=queryFilter) {
			int j =0;
		    for(int i=k; i< queryFilterSize+k; i++) {
		    	criteriaFinal[i] = Criteria.where(
		    			queryFilter.get(j).getField()).is(queryFilter.get(j).getValue());
		    	j++;
			}
		}
		
		Criteria crt = new Criteria();
		if(criteriaFinal.length!=0) {
			crt.andOperator(criteriaFinal);
		}	
		mQuery.addCriteria(crt);
		return mQuery;
	}

	public Query getLookUpQuery(RequestQuery requestQuery) {
		Query mQuery = buildQuery(requestQuery);
		List<SubObject> query = requestQuery.getQuery();
		Criteria[] criteria = new Criteria[query.size()];
		for(int i=0; i< query.size(); i++) {
			criteria[i] = Criteria.where(
					query.get(i).getField()).regex(REGEX_ANY+query.get(i).getValue()+REGEX_ANY, "i");
			mQuery.fields().include(query.get(i).getField());
		}
		Criteria crt = new Criteria();
		crt.orOperator(criteria);
		if (null!=requestQuery.getFilter() && !requestQuery.getFilter().isEmpty()) {	    
		    crt.andOperator(getFilterCriteria(requestQuery));
		}
		mQuery.addCriteria(crt);
		return mQuery;
	}

	public Query buildQuery(RequestQuery requestQuery) {	 
		Query mQuery = new Query();
		mQuery.fields().exclude("_id");
		mQuery.skip(requestQuery.getTotalItems()*(requestQuery.getOffset()-1));
		mQuery.limit(requestQuery.getTotalItems());	 
		return mQuery;	 
	}

}
