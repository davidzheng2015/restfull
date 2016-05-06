package gov.gdgs.zs.api;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
   
    public ApiController(){
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>  API 启动    >>>>>>>>>>>>>>>>>>>>>");
    }
    
    @RequestMapping(value = "/api", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getApiList() {
		Map<String, Object> obj = new HashMap<String,Object>();
		obj.put("project", "gd_zs_mis");
		obj.put("version", "1.0");
		obj.put("apis", "12");
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
    
}
