package com.molcon.escalex.microbiology.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.molcon.escalex.microbiology.mongodao.MongoDataDAO;
import com.molcon.escalex.microbiology.pojo.DocumentBin;
import com.molcon.escalex.microbiology.pojo.HeaderDocuments;
import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.pojo.SetHTMLFile;

@Service
public class ControllerServiceImpl implements ControllerService {

	@Autowired
	MongoDataDAO mongoDAO;
	
	
	public HeaderDocuments getAllHeaders() {
		return mongoDAO.getAllHeaders();
	}
	
	public HeaderDocuments getHeaders() {
		return mongoDAO.getHeaders();
	}
	
	public HeaderDocuments getLookUP(RequestQuery query) {
		return mongoDAO.getLookUPData(query);
	}
	
	public DocumentBin getSearchResult(RequestQuery query){
		return mongoDAO.getSearchData(query);
	}
	public SetHTMLFile findFile(String filePath) throws IOException {
		
		SetHTMLFile st = new SetHTMLFile();
			st.setHtml(new String(Files.readAllBytes(Paths.get(filePath)),
			        StandardCharsets.ISO_8859_1));
		return st;
	}
}
