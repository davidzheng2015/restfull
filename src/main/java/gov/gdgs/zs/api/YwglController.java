package gov.gdgs.zs.api;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.ProjectConstants;
import gov.gdgs.zs.service.YwglService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * 业务管理API controller
 * @author ming
 *
 */

@RestController
@RequestMapping(value = ProjectConstants.URL_PROJECT)
public class YwglController {
	
	@Resource
	private YwglService ywglService;
	
	/**
	 * 业务协议类api
	 * @throws UnsupportedEncodingException 
	 * @para
	 *
	 */
	@RequestMapping(value = "/xygl", method = RequestMethod.GET)
	public  ResponseEntity<Map<String,Object>> getAsideMenu(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value="where", required=false) String where){

		Map<String,Object> obj = ywglService.getYwxy(page,pageSize,where);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}

}
