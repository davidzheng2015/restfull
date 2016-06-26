package gov.gdgs.zs.api;

import java.util.Map;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.AddlrbService;
import gov.gdgs.zs.service.IAddlrbService;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.entity.AsideMenu;
import com.gdky.restfull.entity.ResponseMessage;



@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class LrbController {
	
	@Resource
	private IAddlrbService addlrbService;
	@Resource
	private AddlrbService addlrService;
	
	@RequestMapping(value = "/addlrb", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> addLrb(@RequestBody  Map<String ,Object> obj) {
		Map<String,Object> rs = addlrbService.addLrb(obj);
		return new ResponseEntity<>(rs,HttpStatus.CREATED);
	}
	@RequestMapping(value = "/add/lrb", method = RequestMethod.GET) 
 	public  ResponseEntity<Map<String,Object>> getLr( 
 			@RequestParam(value = "page", required = true) int page, 
 			@RequestParam(value = "pageSize", required = true) int pageSize, 
 			@RequestParam(value="where", required=false) String where){ 
 		Map<String,Object> obj = addlrService.getlrb(page,pageSize,where); 
 		return new ResponseEntity<>(obj,HttpStatus.OK); 
 	} 
	@RequestMapping(value = "/add/lrb/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getLrbById(
			@PathVariable("id") String id) {

		Map<String, Object> obj = addlrService.getlrbById(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	@RequestMapping(value = "/addlrb/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateLrb(@PathVariable("id") String id,
			@RequestBody Map <String,Object> obj) {
		
		addlrService.updateLrb(obj);
		return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

	}
	
	@RequestMapping(value = "/add/lrfpb", method = RequestMethod.GET) 
 	public  ResponseEntity<Map<String,Object>> getLrfpb( 
 			@RequestParam(value = "page", required = true) int page, 
 			@RequestParam(value = "pageSize", required = true) int pageSize, 
 			@RequestParam(value="where", required=false) String where){ 
 		Map<String,Object> obj = addlrService.getlrfpb(page,pageSize,where); 
 		return new ResponseEntity<>(obj,HttpStatus.OK); 
 	} 
	@RequestMapping(value = "/add/lrfpb/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getLrfpbById(
			@PathVariable("id") String id) {

		Map<String, Object> obj = addlrService.getlrfpbById(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addlrfpb", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> addLrfpb(@RequestBody  Map<String ,Object> obj) {
		Map<String,Object> rs = addlrbService.addLrfpb(obj);
		return new ResponseEntity<>(rs,HttpStatus.CREATED);
	}
	@RequestMapping(value = "/addlrfpb/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateLrfpb(@PathVariable("id") String id,
			@RequestBody Map <String,Object> obj) {
		
		addlrService.updateLrfpb(obj);
		return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

	}
	

}
