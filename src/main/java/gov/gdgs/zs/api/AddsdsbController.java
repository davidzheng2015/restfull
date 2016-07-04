package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.Config;


import gov.gdgs.zs.service.AddsdsbService;
import gov.gdgs.zs.service.IAddsdsbService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.entity.ResponseMessage;

@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class AddsdsbController {
	
	@Resource 
	private IAddsdsbService iaddsdsbService;
	
	@Resource
	private AddsdsbService addsdsbService;
	
	 @RequestMapping(value = "/addswsjbb", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> addSwsjbb(@RequestBody  Map<String ,Object> obj) {
			Map<String,Object> rs = iaddsdsbService.AddSwsjbqkb(obj);
			return new ResponseEntity<>(rs,HttpStatus.CREATED);
		}
	 
	 @RequestMapping(value = "/add/swsjbb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getSwsjbb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where){ 
	 		Map<String,Object> obj = addsdsbService.getSwsjbqkb(page, pageSize, where);
	 		return new ResponseEntity<>(obj,HttpStatus.OK); 
	 	} 
	 
	 @RequestMapping(value = "/add/swsjbb/{id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getSwsjbbById(
				@PathVariable("id") String id) {

			Map<String, Object> obj = addsdsbService.getSwsjbqkbById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 @RequestMapping(value = "/add/swsjbbok", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getOK(
				 ) {

			Map<String, Object> obj = addsdsbService.getOK();
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/addswsjbb/{id}", method = RequestMethod.PUT)
		public ResponseEntity<ResponseMessage> updateSwsjbb(@PathVariable("id") String id,
				@RequestBody Map <String,Object> obj) {
			
		 addsdsbService.UpdateSwsjbqkb(obj);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

		}
 //经营规模统计表
	 @RequestMapping(value = "/add/jygmtjb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getJygmtjb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where){ 
	 		Map<String,Object> obj = addsdsbService.getJygmtjb(page, pageSize, where);
	 		return new ResponseEntity<>(obj,HttpStatus.OK); 
	 	} 
	 
	 @RequestMapping(value = "/add/jygmtjb/{id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getJygmtjbById(
				@PathVariable("id") String id) {

			Map<String, Object> obj = addsdsbService.getJygmtjbById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	
	 @RequestMapping(value = "/add/jygmtjbok", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getOK1(
				 ) {

			Map<String, Object> obj = addsdsbService.getOK1();
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/addjygmtjb", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> addJygmtjb(@RequestBody  Map<String ,Object> obj) {
			Map<String,Object> rs = iaddsdsbService.AddJygmtjb(obj);
			return new ResponseEntity<>(rs,HttpStatus.CREATED);
		}
	 
	 @RequestMapping(value = "/addjygmtjb/{id}", method = RequestMethod.PUT)
		public ResponseEntity<ResponseMessage> updateJygmtjb(@PathVariable("id") String id,
				@RequestBody Map <String,Object> obj) {
			
		 addsdsbService.UpdateJygmtjb(obj);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

		}
 //鉴证业务情况统计表
	 @RequestMapping(value = "/add/jzywqktjb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getJzywqktjb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where){ 
	 		Map<String,Object> obj = addsdsbService.getJzywqktjb(page, pageSize, where);
	 		return new ResponseEntity<>(obj,HttpStatus.OK); 
	 	} 
	 @RequestMapping(value = "/add/jzywqktjb/{id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getJzywqktjbById(
				@PathVariable("id") String id) {

			Map<String, Object> obj = addsdsbService.getJzywqktjbById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 @RequestMapping(value = "/add/upyear", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getUpyear(
				 ) {

			Map<String, Object> obj = addsdsbService.getUpyear();
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/addjzywqktjb", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> addJzywqktjb(@RequestBody  Map<String ,Object> obj) {
			Map<String,Object> rs = iaddsdsbService.AddJzywqktjb(obj);
			return new ResponseEntity<>(rs,HttpStatus.CREATED);
		}
	 
	 @RequestMapping(value = "/addjzywqktjb/{id}", method = RequestMethod.PUT)
		public ResponseEntity<ResponseMessage> updateJzywqktjb(@PathVariable("id") String id,
				@RequestBody Map <String,Object> obj) {
			
		 addsdsbService.UpdateJzywqktjb(obj);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

		}

}
