package gov.gdgs.zs.api;


import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.YwglService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 业务管理API controller
 * @author ming
 *
 */

@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class YwglController {
	
	@Resource
	private YwglService ywglService;
	
	/**
	 * 业务协议类api
	 * @para
	 */
	@RequestMapping(value = "/ywbb", method = RequestMethod.GET)
	public  ResponseEntity<Map<String,Object>> getYwbb(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value="where", required=false) String where){ 

		Map<String,Object> obj = ywglService.getYwbb(page,pageSize,where);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ywbb/{hash}", method = RequestMethod.GET)
	public  ResponseEntity<Map<String,Object>> getYwbbById(@PathVariable("hash") String hash){ 
		
		Map<String,Object> obj = ywglService.getYwbbById(hash);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	//客户端用业务报备查询
	@RequestMapping(value="/jg/{hashId}/yw",method = RequestMethod.GET)
	public ResponseEntity<?> getYwbbByJg(
			@PathVariable("hashId") String hashId,
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value="where", required=false) String where){
		Map<String,Object> obj = ywglService.getYwbbByJg(hashId,page,pageSize,where);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	//客户端用机构关联执业税务师和机构信息
	@RequestMapping(value="/ywbbmisc/{jgHashid}",method = RequestMethod.GET)
	public ResponseEntity<?> getYwbbMiscByJg(
			@PathVariable("jgHashid") String jgHashid){
		Map<String,Object> obj = ywglService.getYwbbMiscByJg(jgHashid);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	//客户端提交业务报备信息
	@RequestMapping(value = "/ywbb", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Object>> addYwbb(
			@RequestBody Map<String,Object> values){ 

		Map<String,Object> obj = ywglService.addYwbb(values);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}

}
