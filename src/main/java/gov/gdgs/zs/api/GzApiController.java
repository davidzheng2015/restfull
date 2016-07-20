package gov.gdgs.zs.api;

import gov.gdgs.zs.service.GzApiService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/gzapi")
public class GzApiController {
	
	@Autowired
	private GzApiService gzApiService ;
	
	@RequestMapping(value="/sws/{year}/{month}/{day}/{hour}")
	public ResponseEntity<?> getSws(
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String day,
			@PathVariable String hour){
		Map<String,Object> obj = gzApiService.getSws(year,month,day,hour);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}

}
