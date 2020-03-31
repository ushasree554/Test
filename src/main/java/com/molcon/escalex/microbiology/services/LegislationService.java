package com.molcon.escalex.microbiology.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.molcon.escalex.microbiology.pojo.LegislationModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;

@Service
public interface LegislationService {

	Map<String, Object> indexData(LegislationModel legislationModel) throws Exception;

	Map<String, Object> search(SearchBin searchBin,int start,int limit) throws Exception;

	Map<String, Object> list() throws Exception;

}
