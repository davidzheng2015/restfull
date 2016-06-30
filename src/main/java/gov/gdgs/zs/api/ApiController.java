package gov.gdgs.zs.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.security.CustomUserDetails;

@RestController
public class ApiController {
	@Autowired
		    private HttpServletRequest request;
   
    public ApiController(){
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>  API 启动    >>>>>>>>>>>>>>>>>>>>>");
    }
    
    @RequestMapping(value = "/api", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getApiList() {

    	System.out.println(request.getServletPath());

    	Authentication userName = SecurityContextHolder.getContext().getAuthentication();
    	System.out.println(userName.toString());

		Map<String, Object> obj = new HashMap<String,Object>();
		obj.put("project", "gd_zs_mis");
		obj.put("version", "1.0");
		obj.put("apis", "12");
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/api", method = RequestMethod.POST)
	public ResponseEntity<String> addApiList(@RequestBody Map<String, Object> item) {
    	String str = item.toString();
    	System.out.println(str);
		return new ResponseEntity<>("api responed OK",HttpStatus.CREATED);
	}
    
    @RequestMapping(value = "/protect/api", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAuthApi() {

		Map<String, Object> obj = new HashMap<String,Object>();
		obj.put("security", "auth path");
		obj.put("project", "gd_zs_mis");
		obj.put("version", "1.0");
		obj.put("apis", "12");
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}
