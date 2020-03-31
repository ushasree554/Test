package com.molcon.escalex.microbiology.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
		
		String error = "";
		Map<String, Object>  result = new HashMap<String, Object>();
		String foodCt = searchBin.getFoodCategory();
		String microorganism = searchBin.getMicroorganism();
		if((foodCt == null || foodCt.equals("")) && (microorganism == null || microorganism.equals(""))) {
			error = "foodCategory /  microorganism  field is empty or not found";
		}
		if(!error.equals("")) {
			result.put("error",error);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		result = service.search(searchBin, start, limit);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list() throws Exception{
		Map<String, Object>  result = service.list();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
}
