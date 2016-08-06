package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.HfglService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Config.URI_API_ZS)
public class HfglController {

	@Resource
	private HfglService hfglService;
	
	@RequestMapping(value = "/hyhfjyqk", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> hyhfjnqk(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where) throws Exception  {
		
		return new ResponseEntity<>(hfglService.hyhfjnqk(pn, ps, where),HttpStatus.OK);

	}
	@RequestMapping(value = "/fpdy", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> fpdy(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where) throws Exception  {
		
		return new ResponseEntity<>(hfglService.fpdy(pn, ps, where),HttpStatus.OK);
		
	}
}
