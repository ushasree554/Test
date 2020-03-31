package com.molcon.escalex.microbiology.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.molcon.escalex.microbiology.dao.FoodCategoryDao;
import com.molcon.escalex.microbiology.pojo.FoodCategoryModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.util.FoodCategoryUtil;

@Component
public class FoodCategoryServiceImpl implements FoodCategoryService{

	@Autowired
	FoodCategoryDao foodCategoryDao;
	
	@Override
	public Map<String, Object> test(int id) throws Exception {

		Map<String, Object> result = new  HashMap<>();
		
		result.put("user", id);
		return result;
	}

	@Override
	public Map<String, Object> indexData(FoodCategoryModel foodCategoryModel) throws Exception {
		
		return foodCategoryDao.indexData(foodCategoryModel);
	}

	@Override
	public Map<String, Object> search(SearchBin searchBin,int start,int limit) throws Exception {

		return foodCategoryDao.search(searchBin, start, limit);
	}

	@Override
	public Map<String, Object> list() throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("results", FoodCategoryUtil.getList());
		return result;
		
	}

}
