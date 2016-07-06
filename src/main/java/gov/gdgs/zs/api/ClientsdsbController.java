package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.ClientsdsbService;

import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	 @RequestMapping(value = "/add/hyryqktjb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getHyryqktjb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where,
	 			HttpServletRequest request)
	 			 throws Exception{ 
		    User user =  accountService.getUserFromHeaderToken(request);
	 	
	 		return new ResponseEntity<>(clientsdsbService.getHyryqktjb(page, pageSize, where,user.getJgId(),user.getId()),HttpStatus.OK); 
	 	} 

}
