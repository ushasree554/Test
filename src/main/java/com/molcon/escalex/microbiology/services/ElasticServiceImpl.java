package com.molcon.escalex.microbiology.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molcon.escalex.microbiology.dao.SectionDao;
import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.pojo.SectionInfo;

@Service
public class ElasticServiceImpl implements ElasticService{

	
	@Autowired
	SectionDao sectionDao;
	
	
	public Map<String, Object> getSectionById(int id) throws Exception {
		return sectionDao.getSectionById(id);
	}

	public SectionInfo indexData(SectionInfo section) throws Exception {
		return sectionDao.indexData(section);
	}

	public Map<String, Object>  getIndexs(int start, int offset) throws Exception {
		return sectionDao.getIndexs(start,offset);
	}

	public Map<String, Object>  search(SearchBin searchBin) throws Exception {
		return sectionDao.search(searchBin);
	}

	public Map<String, Object> getSectionId(SearchBin searchBin) throws Exception {
		return sectionDao.getSectionId(searchBin);
	}
	

}
