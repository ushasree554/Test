package com.molcon.escalex.microbiology.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.molcon.escalex.microbiology.dao.LegislationDao;
import com.molcon.escalex.microbiology.pojo.LegislationModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.util.FoodCategoryUtil;
import com.molcon.escalex.microbiology.util.LegislationUtil;

@Component
public class LegislationServiceImpl implements LegislationService{

	@Autowired
	LegislationDao legislationDao;
	
	@Override
	public Map<String, Object> indexData(LegislationModel legislationModel) throws Exception {

		return legislationDao.indexData(legislationModel);
	}

	@Override
	public Map<String, Object> search(SearchBin searchBin,int start,int limit) throws Exception {

		return legislationDao.search(searchBin, start, limit);
	}

	@Override
	public Map<String, Object> list() throws Exception {

		return legislationDao.list();
	}

}
