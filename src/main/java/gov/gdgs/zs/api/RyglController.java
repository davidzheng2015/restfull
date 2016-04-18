package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.ProjectConstants;
import gov.gdgs.zs.dao.RyglDao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + ProjectConstants.URI_API_ZS)
public class RyglController {
	@Resource
	private RyglDao ryglDao;
	
	@RequestMapping(value = "/rys", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> zyrycx(HttpServletRequest request) throws Exception {
		return null;
		
	}
}
