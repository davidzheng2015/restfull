package gov.gdgs.zs.api;

import java.util.Map;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.JDJCService;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Config.URI_API_ZS)
public class JDJCController {

	@Resource
	private JDJCService jdjcService;
	
	@RequestMapping(value = "/jdjc/swsnj1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swsnjcx(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(jdjcService.swsnjcx(pn, ps, where),HttpStatus.OK);

	}

	//执业税务师年检
	@RequestMapping(value = "/jdjc/zyswsnjb", method = RequestMethod.GET) 
	 	public  ResponseEntity<Map<String,Object>> getJygmtjb( 
	 			@RequestParam(value = "page", required = true) int page, 
	 			@RequestParam(value = "pageSize", required = true) int pageSize, 
	 			@RequestParam(value="where", required=false) String where){ 
	 		Map<String,Object> obj = jdjcService.getZyswsnjb(page,pageSize,where); 
	 		return new ResponseEntity<>(obj,HttpStatus.OK); 
	 	} 
	@RequestMapping(value="/jdjc/zyswsnjb/{Id}",method = RequestMethod.GET)
      public  ResponseEntity<Map<String,Object>> getJygmtjbById(@PathVariable("Id") long id){ 		
		Map<String,Object> obj = jdjcService.getZyswsnjbById(id);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	 }
}
