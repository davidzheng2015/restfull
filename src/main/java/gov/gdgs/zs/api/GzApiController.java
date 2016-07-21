package gov.gdgs.zs.api;
/**
 * 开放给广州局用的数据api，包含税务师、机构、业务备案、业务协议
 */
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
	
	//获取税务师数据
	@RequestMapping(value="/sws/{year}/{month}/{day}/{hour}")
	public ResponseEntity<?> getSws(
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String day,
			@PathVariable String hour){
		Map<String,Object> obj = gzApiService.getSws(year,month,day,hour);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	//获取机构数据
	@RequestMapping(value="/swsjg/{year}/{month}/{day}/{hour}")
	public ResponseEntity<?> getJg(
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String day,
			@PathVariable String hour){
		Map<String,Object> obj = gzApiService.getSwsjg(year,month,day,hour);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	//获取鉴证报告数据
	@RequestMapping(value="/ywba/{year}/{month}/{day}/{hour}")
	public ResponseEntity<?> getYwba(
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String day,
			@PathVariable String hour){
		Map<String,Object> obj = gzApiService.getYwba(year,month,day,hour);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	//获取协议数据
	@RequestMapping(value="/zsxy/{year}/{month}/{day}/{hour}")
	public ResponseEntity<?> getZsxy(
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String day,
			@PathVariable String hour){
		Map<String,Object> obj = gzApiService.getZsxy(year,month,day,hour);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}

}
