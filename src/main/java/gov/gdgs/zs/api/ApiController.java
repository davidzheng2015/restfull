package gov.gdgs.zs.api;

import gov.gdgs.zs.configuration.Config;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class ApiController {
	
   
    public ApiController(){
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>  API 启动    >>>>>>>>>>>>>>>>>>>>>");
    }
    
}
