package gov.gdgs.zs.api;

import java.util.Map;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.JDJCService;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
