package com.molcon.escalex.microbiology.services;

import java.util.Map;

import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.pojo.SectionInfo;


public interface ElasticService {

	public SectionInfo indexData(SectionInfo section) throws Exception;
	public Map<String, Object>  getSectionById(int id) throws Exception;
	public Map<String, Object>  getIndexs(int start, int offset) throws Exception;
	public Map<String, Object> search(SearchBin searchBin) throws Exception;
	public Map<String, Object> getSectionId(SearchBin searchBin) throws Exception;
}
