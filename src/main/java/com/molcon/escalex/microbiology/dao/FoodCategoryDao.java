package com.molcon.escalex.microbiology.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.molcon.escalex.microbiology.pojo.FoodCategoryModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;

@Repository
public class FoodCategoryDao {

	private final String INDEX = "food_category";
	private final String TYPE = "doc";  

	@Autowired
	private RestHighLevelClient restHighLevelClient;
	@Autowired
	private ObjectMapper objectMapper;

	public Map<String, Object> indexData(FoodCategoryModel foodCategoryModel) throws Exception{

		Map<String, Object> result = new HashMap<String, Object>();

		foodCategoryModel.setId(UUID.randomUUID().toString());
		Map dataMap = objectMapper.convertValue(foodCategoryModel, Map.class);
		System.out.println(foodCategoryModel.getId());
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, foodCategoryModel.getId())
				.source(dataMap);
		IndexResponse response = restHighLevelClient.index(indexRequest);
		System.out.println(response);
		result.put("result", response);
		return result;
	}

	public Map<String, Object> search(SearchBin searchBin,int start,int limit) throws Exception {

		Map<String, Object> result  = new HashMap<>();
		List<Map<String, Object>> categoryList = new ArrayList<>();

		Map<String, Object> sourceAsMap  = null;
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 

		/*String searchText = "\"*"+searchBin.getTerm().toLowerCase()+"\"*";
		sourceBuilder.query(QueryBuilders.queryStringQuery(searchText)
				.field("section_title").field("sub_section_text").defaultOperator(Operator.AND));*/

		QueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(searchBin.getTerm().toLowerCase(),"food_product","food_product_examples")
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

		sourceBuilder.from(start);
		sourceBuilder.size(limit);
		//	sourceBuilder.sort(new FieldSortBuilder("sub_section_id").order(SortOrder.ASC));  

		//sourceBuilder.highlighter(new HighlightBuilder().field("food_product", 200, 3).field("food_product_examples",202,3));

		String[] includeFields = new String[] {"food_product","food_product_examples","sub_section_id"};
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
			String foodCategory = (String) sourceAsMap.get("food_product");
			int subSectionId = (int) sourceAsMap.get("sub_section_id");
			List<String> foodProductExamples = (List<String>) sourceAsMap.get("food_product_examples");
			Map<String, Object> dataMap  = new HashMap<>();
			dataMap.put("subSectionId", subSectionId);
			dataMap.put("foodCategory", foodCategory);
			dataMap.put("foodProductExamples", foodProductExamples);

			/*Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			HighlightField highlight = highlightFields.get("food_product"); 
			if(highlight!=null) {
				Text[] fragments = highlight.fragments();  
				String fragmentString = fragments[0].string();
				dataMap.put("snippet", fragmentString);
			}else {
			highlight = highlightFields.get("food_product_examples"); 	
			if(highlight!=null) {
				Text[] fragments = highlight.fragments();  
				String fragmentString = fragments[0].string();
				dataMap.put("snippet", fragmentString);
			}
			}*/

			categoryList.add(dataMap);

		}
		result.put("results", categoryList);
		result.put("total", hits.totalHits);
		return result;

	}

}
