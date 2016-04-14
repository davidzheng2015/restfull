package gov.gdgs.zs.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.ProjectConstants;
import gov.gdgs.zs.service.YwglService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;


/**
 * 业务管理API controller
 * @author ming
 *
 */

@RestController
@RequestMapping(value = Constants.URL_PROJECT)
public class YwglController {
	
	@Resource
	private YwglService ywglService;
	
	/**
	 * 业务协议类api
	 * @para
	 *
	 */
	@RequestMapping(value = "/zsxygl", method = RequestMethod.GET)
	public  ResponseEntity<Map<String,Object>> getAsideMenu(
			@RequestParam(value = "page", required = false) int page,
			@RequestParam(value = "pageSize", required = false) int pageSize) {

		Map<String,Object> obj = ywglService.getYwxy(page,pageSize);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}

}
