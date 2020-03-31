package com.molcon.escalex.microbiology;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.molcon.escalex.microbiology.pojo.LegislationModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.services.LegislationService;

@RestController
@RequestMapping("/legislation")
public class LegislationController {

	@Autowired
	LegislationService service;
	
	
	@PostMapping("/indexData")
	public ResponseEntity<Map<String, Object>> indexData(@RequestBody LegislationModel  legislationModel) throws Exception{
		
		Map<String, Object>  result = service.indexData(legislationModel);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> search(@RequestBody SearchBin searchBin,@RequestParam int start,int limit) throws Exception{
		
		Map<String, Object>  result = service.search(searchBin, start, limit);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
}
