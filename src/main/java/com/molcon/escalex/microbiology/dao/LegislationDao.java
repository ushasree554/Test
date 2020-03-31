package com.molcon.escalex.microbiology.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.molcon.escalex.microbiology.pojo.LegislationModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;

@Repository
public class LegislationDao {

	private final String INDEX = "legislation";
	private final String TYPE = "doc";  

	@Autowired
	private RestHighLevelClient restHighLevelClient;
	@Autowired
	private ObjectMapper objectMapper;

	public Map<String, Object> indexData(LegislationModel legislationModel) throws Exception{

		Map<String, Object> result = new HashMap<String, Object>();

		legislationModel.setId(UUID.randomUUID().toString());
		Map dataMap = objectMapper.convertValue(legislationModel, Map.class);
		System.out.println(legislationModel.getId());
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, legislationModel.getId())
				.source(dataMap);
		IndexResponse response = restHighLevelClient.index(indexRequest);
		System.out.println(response);
		result.put("result", response);
		return result;
	}

	public Map<String, Object> search(SearchBin searchBin,int start,int limit) throws Exception {

		Map<String, Object> result  = new HashMap<>();
		List<Map<String, Object>> legislationList = new ArrayList<>();
		Map<String, Object> sourceAsMap  = null;
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 

		QueryBuilder queryBuilder = null;
		if(searchBin.getFoodCategory()!=null && !searchBin.getFoodCategory().equals("")
				&& searchBin.getMicroorganism()!=null && !searchBin.getMicroorganism().equals("")) {
			QueryBuilder matchQueryBuilder1 = QueryBuilders.matchQuery("food_category", searchBin.getFoodCategory().toLowerCase());
			QueryBuilder matchQueryBuilder2 = QueryBuilders.matchQuery("microorganism", searchBin.getMicroorganism().toLowerCase());
			queryBuilder = QueryBuilders.boolQuery().must(matchQueryBuilder1).must(matchQueryBuilder2);
		}else if(searchBin.getFoodCategory()!=null && !searchBin.getFoodCategory().equals("")) {

			QueryBuilder matchQueryBuilder1 = QueryBuilders.matchQuery("food_category", searchBin.getFoodCategory().toLowerCase());
			queryBuilder = QueryBuilders.boolQuery().must(matchQueryBuilder1);
		}else {
			QueryBuilder matchQueryBuilder2 = QueryBuilders.matchQuery("microorganism", searchBin.getMicroorganism().toLowerCase());
			queryBuilder = QueryBuilders.boolQuery().must(matchQueryBuilder2);
		}
		sourceBuilder.query(queryBuilder);
		sourceBuilder.from(start);
		sourceBuilder.size(limit);

		//sourceBuilder.highlighter(new HighlightBuilder().field("food_category", 500, 3).field("microorganism",202,3));

		String[] includeFields = new String[] {"food_category","microorganism","sub_section_id","legislation"};
		String[] excludeFields = new String[] {"id"};
		sourceBuilder.fetchSource(includeFields, excludeFields);

		searchRequest.source(sourceBuilder); 
		searchRequest.indices(INDEX);
		searchRequest.types(TYPE);
		SearchResponse searchResponse = null;


		searchResponse = restHighLevelClient.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {

			sourceAsMap = hit.getSourceAsMap();		
			String foodCategory = (String) sourceAsMap.get("food_category");
			String microorganism = (String) sourceAsMap.get("microorganism");
			String legislation = (String) sourceAsMap.get("legislation");
			int subSectionId = (int) sourceAsMap.get("sub_section_id");
			Map<String, Object> dataMap  = new HashMap<>();
			dataMap.put("subSectionId", subSectionId);
			dataMap.put("foodCategory", foodCategory);
			dataMap.put("microorganism", microorganism);
			dataMap.put("legislation", legislation);

			/*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			HighlightField highlight = highlightFields.get("food_category"); 
			if(highlight!=null) {
				Text[] fragments = highlight.fragments();  
				String fragmentString = fragments[0].string();
				dataMap.put("foodCategory", fragmentString);
			}
			highlight = highlightFields.get("microorganism"); 	
			if(highlight!=null) {
				Text[] fragments = highlight.fragments();  
				String fragmentString = fragments[0].string();
				dataMap.put("microorganism", fragmentString);
			}*/


			legislationList.add(dataMap);
		}

		result.put("results", legislationList);
		result.put("total", hits.totalHits);
		return result;

	}

	public Map<String, Object> list() throws Exception {

		Map<String, Object> result  = new HashMap<>();
		Set<String> list = new TreeSet<>();
		Map<String, Object> sourceAsMap  = null;
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 

		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

		sourceBuilder.query(queryBuilder);

		//sourceBuilder.highlighter(new HighlightBuilder().field("food_product", 200, 3).field("food_product_examples",202,3));

		String[] includeFields = new String[] {"food_category","legislation"};
		String[] excludeFields = new String[] {"id"};
		sourceBuilder.fetchSource(includeFields, excludeFields);

		searchRequest.source(sourceBuilder); 
		searchRequest.indices(INDEX);
		searchRequest.types(TYPE);
		SearchResponse searchResponse = null;


		searchResponse = restHighLevelClient.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {

			sourceAsMap = hit.getSourceAsMap();		
			String foodCategory = (String) sourceAsMap.get("food_category");
			String legislation = (String) sourceAsMap.get("legislation");
			list.add(foodCategory);
			list.add(legislation);
		}

		result.put("results", list);
		return result;
	}
}
