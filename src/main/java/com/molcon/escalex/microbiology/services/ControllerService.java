package com.molcon.escalex.microbiology.services;


import java.io.IOException;



import com.molcon.escalex.microbiology.pojo.DocumentBin;
import com.molcon.escalex.microbiology.pojo.HeaderDocuments;
import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.pojo.SetHTMLFile;

public interface ControllerService {

	public HeaderDocuments getAllHeaders();
	public HeaderDocuments getHeaders();
	public HeaderDocuments getLookUP(RequestQuery query);
	public DocumentBin getSearchResult(RequestQuery query);
	public SetHTMLFile findFile(String id) throws IOException;
}
