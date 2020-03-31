package com.molcon.escalex.microbiology.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.molcon.escalex.microbiology.pojo.FoodCategoryModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;

@Service
public interface FoodCategoryService {

	public Map<String, Object>  test(int id) throws Exception;

	public Map<String, Object> indexData(FoodCategoryModel foodCategoryModel) throws Exception;

	public Map<String, Object> search(SearchBin searchBin,int start,int limit) throws Exception;

	public Map<String, Object> list() throws Exception;
}
