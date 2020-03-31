package com.molcon.escalex.microbiology;


import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.molcon.escalex.microbiology.pojo.DocumentBin;
import com.molcon.escalex.microbiology.pojo.HeaderDocuments;
import com.molcon.escalex.microbiology.pojo.RequestQuery;
import com.molcon.escalex.microbiology.pojo.SetHTMLFile;
import com.molcon.escalex.microbiology.services.ControllerService;

@RestController
public class DataController {

	@Autowired
	ControllerService service;
	
	 @GetMapping("/api/ping")
		public String status() {	
	     return "API IS UP AND RUNNING SUCCESSFULLY";		    	
		}
	 
	 @GetMapping("/api/header")
	 public HeaderDocuments getHeader() {
		 return service.getHeaders();
	 }
	 
	 @GetMapping("/api/all-header")
	 public HeaderDocuments getAllHeaders() {
		 return service.getAllHeaders();
	 }
	 
	 @PostMapping("/api/lookup")
	 public HeaderDocuments getCategory(@RequestBody RequestQuery query) {
		 return service.getLookUP(query);
	 }
	 
	 @PostMapping("/api/search")
	 public DocumentBin getSearchResult(@RequestBody RequestQuery query) {
		 return service.getSearchResult(query);
	 }
	 
	 @GetMapping(path = "/getfile/{id}")
		public SetHTMLFile getCleanedXML(@PathVariable int id) throws IOException  {
		 String file = "/home/irfan.m/Desktop/escalex_microbiology/book/book1.html";
			return service.findFile(file);
		}
	 
	 @GetMapping(path = "/html")
		public SetHTMLFile getMainHtml() throws IOException {
		 String file = "/home/irfan.m/Desktop/escalex_microbiology/book/book.html";
			return service.findFile(file);
		}
	 
}
