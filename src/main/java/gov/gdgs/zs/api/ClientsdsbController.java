package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.ClientsdsbService;

import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class ClientsdsbController {
	
	@Resource
	AccountService accountService;
	
	@Resource
	private ClientsdsbService clientsdsbService;
	/*
	 * 行业人员统计表
	 */

	 @RequestMapping(value = "/add/hyryqktjb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getHyryqktjb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where,HttpServletRequest request)
	 			throws Exception{ 
		   User user =  accountService.getUserFromHeaderToken(request);
	 	
	 		return new ResponseEntity<>(clientsdsbService.getHyryqktjb(page, pageSize,user.getJgId(), where),HttpStatus.OK); 
	 	} 
	 @RequestMapping(value = "/add/hyryqktjb/{id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getHyryqktjbById(
				@PathVariable("id") String id) {

			Map<String, Object> obj = clientsdsbService.getHyryqktjbById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 @RequestMapping(value = "/addhyryqktjb", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> addHyryqktjb(@RequestBody  Map<String ,Object> obj,HttpServletRequest request) 
		throws Exception{
		 try{
			 User user =  accountService.getUserFromHeaderToken(request);
			 obj.put("use_id",user.getId());
			 obj.put("jg_id", user.getJgId());
		 }catch (Exception e){	 
		 }
		
			return new ResponseEntity<>(clientsdsbService.AddHyryqktjb(obj),HttpStatus.CREATED);
		}
	 @RequestMapping(value = "/addhyryqktjb/{id}", method = RequestMethod.PUT)
		public ResponseEntity<ResponseMessage> updateHyryqktjb(@PathVariable("id") String id,
				@RequestBody Map <String,Object> obj,HttpServletRequest request) 
				throws Exception{
		 try{
			 User user =  accountService.getUserFromHeaderToken(request);
			 obj.put("use_id",user.getId());
			 obj.put("jg_id", user.getJgId());
		 }catch (Exception e){	 
		 }

		    clientsdsbService.UpdateHyryqktjb(obj);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

		}
	 
	 @RequestMapping(value = "/add/hyryqktjbok/{jg_id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getOk(
				@PathVariable("jg_id") String jgid) {

			Map<String, Object> obj = clientsdsbService.getOK(jgid);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 /*
	  * 经营收入统计表
	  */
	 
	 @RequestMapping(value = "/add/jysrqkb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getJysrqkb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where,HttpServletRequest request)
	 			throws Exception{ 
		   User user =  accountService.getUserFromHeaderToken(request);
	 	
	 		return new ResponseEntity<>(clientsdsbService.getJysrqkb(page, pageSize,user.getJgId(), where),HttpStatus.OK); 
	 	} 
	 @RequestMapping(value = "/add/jysrqkb/{id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getJysrqkbById(
				@PathVariable("id") String id) {

			Map<String, Object> obj = clientsdsbService.getJysrqkbById(id);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}
	 
	 @RequestMapping(value = "/addjysrqkb", method = RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> addJysrqkb(@RequestBody  Map<String ,Object> obj,HttpServletRequest request) 
		throws Exception{
		 try{
			 User user =  accountService.getUserFromHeaderToken(request);
			 obj.put("use_id",user.getId());
			 obj.put("jg_id", user.getJgId());
		 }catch (Exception e){	 
		 }
		
			return new ResponseEntity<>(clientsdsbService.AddJysrqkb(obj),HttpStatus.CREATED);
		}
	 @RequestMapping(value = "/addjysrqkb/{id}", method = RequestMethod.PUT)
		public ResponseEntity<ResponseMessage> updateJysrqkb(@PathVariable("id") String id,
				@RequestBody Map <String,Object> obj,HttpServletRequest request) 
				throws Exception{
		 try{
			 User user =  accountService.getUserFromHeaderToken(request);
			 obj.put("use_id",user.getId());
			 obj.put("jg_id", user.getJgId());
		 }catch (Exception e){	 
		 }

		    clientsdsbService.UpdateJysrqkb(obj);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

		}
	 
	 @RequestMapping(value = "/add/upyear1/{jg_id}", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> getUpyear(
				@PathVariable("jg_id") String jgid ) {

			Map<String, Object> obj = clientsdsbService.getUpyear(jgid);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}

}
