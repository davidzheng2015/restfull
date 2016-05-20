package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.FzylsjlService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class FzylsjlController {
	@Resource
	private FzylsjlService fzylsjlService;
	
	
	@RequestMapping(value = "/fzylsjl/zjjl", method = RequestMethod.GET) 
 	public  ResponseEntity<Map<String,Object>> getFzyzjjlb( 
 			@RequestParam(value = "page", required = true) int page, 
 			@RequestParam(value = "pageSize", required = true) int pageSize, 
 			@RequestParam(value="where", required=false) String where){ 
 		Map<String,Object> obj = fzylsjlService.getFzyzjjlb(page,pageSize,where); 
 		return new ResponseEntity<>(obj,HttpStatus.OK); 
 	} 
	
	@RequestMapping(value = "/fzylsjl/zxjl", method = RequestMethod.GET) 
 	public  ResponseEntity<Map<String,Object>> getFzyzxjlb( 
 			@RequestParam(value = "page", required = true) int page, 
 			@RequestParam(value = "pageSize", required = true) int pageSize, 
 			@RequestParam(value="where", required=false) String where){ 
 		Map<String,Object> obj = fzylsjlService.getFzyzxjlb(page,pageSize,where); 
 		return new ResponseEntity<>(obj,HttpStatus.OK); 
 	} 
	
	@RequestMapping(value = "/fzylsjl/zzyjl", method = RequestMethod.GET) 
 	public  ResponseEntity<Map<String,Object>> getFzyzzyjlb( 
 			@RequestParam(value = "page", required = true) int page, 
 			@RequestParam(value = "pageSize", required = true) int pageSize, 
 			@RequestParam(value="where", required=false) String where){ 
 		Map<String,Object> obj = fzylsjlService.getFzyzzyjlb(page,pageSize,where); 
 		return new ResponseEntity<>(obj,HttpStatus.OK); 
 	}

}
