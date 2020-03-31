package com.molcon.escalex.microbiology;

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

import com.molcon.escalex.microbiology.pojo.FoodCategoryModel;
import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.services.FoodCategoryService;

@RestController
@RequestMapping("/food")
public class FoodCategoryController {

	@Autowired
	FoodCategoryService service;
	
	@GetMapping("/test")
	public ResponseEntity<Map<String, Object>> projects(@RequestParam int userId) throws Exception {

		Map<String, Object>  result = service.test(userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/indexData")
	public ResponseEntity<Map<String, Object>> indexData(@RequestBody FoodCategoryModel  foodCategoryModel) throws Exception{
		
		Map<String, Object>  result = service.indexData(foodCategoryModel);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> search(@RequestBody SearchBin  searchBin,@RequestParam int start,@RequestParam int limit) throws Exception{
		
		Map<String, Object>  result = service.search(searchBin,start,limit);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
}
