package com.molcon.escalex.microbiology.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.pojo.SectionInfo;

@Repository
public class SectionDao {

	private final String INDEX = "microbiology_test";
	private final String TYPE = "doc";  

	@Autowired
	private RestHighLevelClient restHighLevelClient;
	@Autowired
	private ObjectMapper objectMapper;

	public SectionInfo indexData(SectionInfo section) throws Exception{
		section.setId(UUID.randomUUID().toString());
		Map dataMap = objectMapper.convertValue(section, Map.class);
		System.out.println(section.getSub_section_display_id());
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, section.getId())
				.source(dataMap);
		IndexResponse response = restHighLevelClient.index(indexRequest);
		//System.out.println(response);

		return section;
	}

	public Map<String, Object> getSectionById(int id) throws Exception {

		Map<String, Object> result  = new HashMap<>();
		Map<String, Object> sourceAsMap  = new HashMap<>();

		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchQuery("sub_section_id", id));

		String[] includeFields = new String[] {"sub_section_id","section_title", "sub_section_title",
				"sub_section_display","sub_section_display_id","section_display_id"};
		String[] excludeFields = new String[] {"sub_section_text"};
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
			result.put("id", sourceAsMap.get("sub_section_id"));
			result.put("section", sourceAsMap.get("section_title"));
			result.put("subSection", sourceAsMap.get("sub_section_title"));
			result.put("text", sourceAsMap.get("sub_section_display"));
			
			result.put("subSectionDisplayId", sourceAsMap.get("sub_section_display_id"));
			result.put("sectionDisplayId", sourceAsMap.get("section_display_id"));
		}
		
		return result;
	
	}

	public Map<String, Object>  getIndexs(int start, int offset) throws Exception {

		Map<String, Object> result  = new HashMap<>();
		Map<String, List<Map<String, Object>>> sectionMap = new LinkedHashMap<>();

		Map<String, Object> sourceAsMap  = null;
		SearchRequest searchRequest = new SearchRequest(); 

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchAllQuery()); 
		sourceBuilder.from(start);
		sourceBuilder.size(offset);
		sourceBuilder.sort(new FieldSortBuilder("sub_section_id").order(SortOrder.ASC));  

		String[] includeFields = new String[] {"sub_section_id","section_title", "sub_section_title","sub_section_display_id","section_display_id"};
		String[] excludeFields = new String[] {"sub_section_text","sub_section_display"};
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
			String sectionTitle = (String) sourceAsMap.get("section_title");
			int subSectionId = (int) sourceAsMap.get("sub_section_id");
			String subSectionTitle = (String) sourceAsMap.get("sub_section_title");
			double subSectionDisplayId = (double) sourceAsMap.get("sub_section_display_id");
			int sectionDisplayId = (int) sourceAsMap.get("section_display_id");

			sectionTitle = sectionTitle + "<BR>" +  sectionDisplayId;
			
			Map<String, Object> dataMap  = new HashMap<>();
			dataMap.put("id", subSectionId);
			dataMap.put("subSection", subSectionTitle);
			dataMap.put("subSectionDisplayId",subSectionDisplayId);

			List<Map<String, Object>> sectionList = null;
			if(sectionMap.containsKey(sectionTitle)) {
				sectionList = sectionMap.get(sectionTitle);
				sectionList.add(dataMap);
			}else {
				sectionList = new ArrayList<>();
				sectionList.add(dataMap);					
			}
			sectionMap.put(sectionTitle, sectionList);

		}
		List<Map<String, Object>> resultList = new ArrayList<>();
		Set<String> sectionSet = sectionMap.keySet();
		for(String section : sectionSet) {				
			Map<String, Object> sectionInfoMap  = new HashMap<>();
			if(section.split("<BR>").length == 2) {
				sectionInfoMap.put("title", section.split("<BR>")[0]);
				sectionInfoMap.put("titleId", Integer.parseInt(section.split("<BR>")[1]));
				sectionInfoMap.put("data", sectionMap.get(section));
				resultList.add(sectionInfoMap);		
			}
								
		}			

		result.put("results", resultList);
		result.put("total", hits.totalHits);
		return result;

	}

	public Map<String, Object> search(SearchBin searchBin) throws Exception {


		Map<String, Object> result  = new HashMap<>();
		Map<String, List<Map<String, Object>>> sectionMap = new LinkedHashMap<>();

		Map<String, Object> sourceAsMap  = null;
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		
		/*String searchText = "\"*"+searchBin.getTerm().toLowerCase()+"\"*";
		sourceBuilder.query(QueryBuilders.queryStringQuery(searchText)
				.field("section_title").field("sub_section_text").defaultOperator(Operator.AND));*/
		
		QueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(searchBin.getTerm().toLowerCase(),"section_title","sub_section_text")
               /*.fuzziness(Fuzziness.ZERO)
                .prefixLength(5)
                .maxExpansions(10);	*/	
				;
		sourceBuilder.query(matchQueryBuilder);
		
		/*QueryBuilder matchQueryBuilder = QueryBuilders.wildcardQuery("sub_section_text",searchBin.getTerm().toLowerCase()+"*")
				.queryName("elasttc~AUTO*");	               
		sourceBuilder.query(matchQueryBuilder);*/
		
		/*QueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("sub_section_text",searchBin.getTerm().toLowerCase());
		sourceBuilder.query(matchQueryBuilder);*/

		sourceBuilder.from(searchBin.getStart());
		
		sourceBuilder.size(searchBin.getOffset());
	//	sourceBuilder.sort(new FieldSortBuilder("sub_section_id").order(SortOrder.ASC));  

		sourceBuilder.highlighter(new HighlightBuilder().field("sub_section_text", 200, 3));

		String[] includeFields = new String[] {"sub_section_id","section_title", "sub_section_title",
				"sub_section_text","sub_section_display_id","section_display_id"};
		String[] excludeFields = new String[] {"sub_section_display"};
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
			String sectionTitle = (String) sourceAsMap.get("section_title");
			int subSectionId = (int) sourceAsMap.get("sub_section_id");
			String subSectionTitle = (String) sourceAsMap.get("sub_section_title");
			double subSectionDisplayId = (double) sourceAsMap.get("sub_section_display_id");
			int sectionDisplayId = (int) sourceAsMap.get("section_display_id");

			sectionTitle = sectionTitle + "<BR>" +  sectionDisplayId;
			
			Map<String, Object> dataMap  = new HashMap<>();
			dataMap.put("id", subSectionId);
			dataMap.put("subSectionDisplayId", subSectionDisplayId);

			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			HighlightField highlight = highlightFields.get("sub_section_text"); 
			if(highlight!=null) {
				Text[] fragments = highlight.fragments();  
				String fragmentString = fragments[0].string();
				dataMap.put("snippet", fragmentString);
			}else {
				dataMap.put("snippet", subSectionTitle);				
			}
			
			


			List<Map<String, Object>> sectionList = null;
			if(sectionMap.containsKey(sectionTitle)) {
				sectionList = sectionMap.get(sectionTitle);
				sectionList.add(dataMap);
			}else {
				sectionList = new ArrayList<>();
				sectionList.add(dataMap);					
			}
			sectionMap.put(sectionTitle, sectionList);
		}
		List<Map<String, Object>> resultList = new ArrayList<>();
		Set<String> sectionSet = sectionMap.keySet();
		for(String section : sectionSet) {			
			
			if(section.split("<BR>").length == 2) {
				Map<String, Object> sectionInfoMap  = new HashMap<>();
				sectionInfoMap.put("title", section.split("<BR>")[0]);
				sectionInfoMap.put("titleId", section.split("<BR>")[1]);
				sectionInfoMap.put("data", sectionMap.get(section));
				resultList.add(sectionInfoMap);
			}
										
		}			

		result.put("results", resultList);
		result.put("total", hits.totalHits);
		return result;
	}

	public Map<String, Object> getSectionId(SearchBin searchBin) throws Exception {

		Map<String, Object> result  = new HashMap<>();
		Map<String, Object> sourceAsMap  = new HashMap<>();

		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.matchPhraseQuery("sub_section_title", searchBin.getTerm()));

		String[] includeFields = new String[] {"sub_section_id","section_title", "sub_section_title"};
		String[] excludeFields = new String[] {"sub_section_text"};
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
			result.put("id", sourceAsMap.get("sub_section_id"));
			result.put("section", sourceAsMap.get("section_title"));
			result.put("subSection", sourceAsMap.get("sub_section_title"));
			
		}
		
		return result;
	
	}


}
