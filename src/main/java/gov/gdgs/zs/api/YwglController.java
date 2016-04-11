package gov.gdgs.zs.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.ProjectConstants;
import gov.gdgs.zs.service.YwglService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;


/**
 * 业务管理API controller
 * @author ming
 *
 */

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + ProjectConstants.URI_API_ZS)
public class YwglController {
	
	@Resource
	private YwglService ywglService;
	
	/**
	 * 业务协议类api
	 * @para
	 *
	 */
	@RequestMapping(value = "/ywxy", method = RequestMethod.GET)
	public  ResponseEntity<List<Map<String,Object>>> getAsideMenu() {
		List<Map<String,Object>> ls = ywglService.getYwxy();
		return new ResponseEntity<>(ls,HttpStatus.OK);
	}

}
