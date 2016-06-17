package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.AddcwbbService;
import gov.gdgs.zs.service.IAddcwbbService;

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
   public class AddcwbbController {
	 
	 @Resource
	 private IAddcwbbService iaddcwbbService;
	 @Resource
	 private AddcwbbService addcwbbService;
	
	 
	 @RequestMapping(value = "/addxjllb", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> addXjllb(@RequestBody  Map<String ,Object> obj) {
			Map<String,Object> rs = iaddcwbbService.AddXjllb(obj);
			return new ResponseEntity<>(rs,HttpStatus.CREATED);
		}
	 
	 @RequestMapping(value = "/add/xjllb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getXjllb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where){ 
	 		Map<String,Object> obj = addcwbbService.getXjllb(page, pageSize, where);
	 		return new ResponseEntity<>(obj,HttpStatus.OK); 
	 	} 
	 
	 @RequestMapping(value = "/add/xjllb/{id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getLxjllbById(
				@PathVariable("id") String id) {

			Map<String, Object> obj = addcwbbService.getXjllbById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/addxjllb/{id}", method = RequestMethod.PUT)
		public ResponseEntity<ResponseMessage> updateXjllb(@PathVariable("id") String id,
				@RequestBody Map <String,Object> obj) {
			
		 addcwbbService.UpdateXjllb(obj);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

		}
	 
	 @RequestMapping(value = "/addzcfzb", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> addZcfzb(@RequestBody  Map<String ,Object> obj) {
			Map<String,Object> rs = iaddcwbbService.AddZcfzb(obj);
			return new ResponseEntity<>(rs,HttpStatus.CREATED);
		}
	 
	 @RequestMapping(value = "/add/zcfzb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getZcfzb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where){ 
	 		Map<String,Object> obj = addcwbbService.getZcfzb(page, pageSize, where);
	 		return new ResponseEntity<>(obj,HttpStatus.OK); 
	 	}
	 
	 @RequestMapping(value = "/add/zcfzb/{id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getLzcfzbById(
				@PathVariable("id") String id) {

			Map<String, Object> obj = addcwbbService.getZcfzbById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/addzcfzb/{id}", method = RequestMethod.PUT)
		public ResponseEntity<ResponseMessage> updateZcfzb(@PathVariable("id") String id,
				@RequestBody Map <String,Object> obj) {
			
		 addcwbbService.UpdateZcfzb(obj);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

		}

}
