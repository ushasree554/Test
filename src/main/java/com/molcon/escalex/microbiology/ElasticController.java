package com.molcon.escalex.microbiology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.molcon.escalex.microbiology.pojo.SearchBin;
import com.molcon.escalex.microbiology.pojo.SectionInfo;
import com.molcon.escalex.microbiology.services.ElasticService;

import java.util.Map;

@RestController
@RequestMapping("/elastic")
public class ElasticController {

	@Autowired
	private ElasticService elasticService;	
    
    @PostMapping("/indexData")
    public SectionInfo insertData(@RequestBody SectionInfo sectionInfo) throws Exception {
      return elasticService.indexData(sectionInfo);
    }
    
    @GetMapping("/section/{id}")
    public Map<String, Object>  getSectionById(@PathVariable int id) throws Exception{
      return elasticService.getSectionById(id);
    }
    
    @GetMapping("/index")
    public Map<String, Object>  getIndexs(@RequestParam int start,@RequestParam int offset) throws Exception{
      return elasticService.getIndexs(start,offset);
    }
    
   @PostMapping("/search")
   public Map<String, Object>  search(@RequestBody SearchBin searchBin) throws Exception{
     return elasticService.search(searchBin);
    }
    
    @PostMapping("/getSectionId")
    public Map<String, Object>  getSectionId(@RequestBody SearchBin searchBin) throws Exception{
      return elasticService.getSectionId(searchBin);
    }
    
}
