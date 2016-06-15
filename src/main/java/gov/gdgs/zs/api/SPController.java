package gov.gdgs.zs.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.SPservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Config.URI_API_ZS)
public class SPController {
	@Resource
	AccountService accountService;
	
	@Resource
	private SPservice spPservice;
	
	@RequestMapping(value = "/zjsh/wspcx1", method = RequestMethod.GET)
	public ResponseEntity<?> wspcx(
			HttpServletRequest request ) throws Exception{
		User user =  accountService.getUserFromHeaderToken(request);
		return new ResponseEntity<>(spPservice.wspcx(user.getId()),HttpStatus.OK);
	}
	@RequestMapping(value = "/zjsh/cklc1", method = RequestMethod.GET)
	public ResponseEntity<?> cklc(@RequestParam(value = "lid", required = true) int lid ) throws Exception{
		return new ResponseEntity<>(spPservice.cklc(lid),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/zjsh/wspxq1", method = RequestMethod.GET)
	public ResponseEntity<?> wspxq(@RequestParam(value = "lcid", required = true) int lcid,
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			HttpServletRequest request ) throws Exception{
		User user =  accountService.getUserFromHeaderToken(request);
		return new ResponseEntity<>(spPservice.swsbgsp(pn,ps,user.getId(),lcid),HttpStatus.OK);
	}
	@RequestMapping(value = "/zjsh/wspxq/bgxx1", method = RequestMethod.GET)
	public ResponseEntity<?> swsbgspxx(@RequestParam(value = "sjid", required = true) int sjid) throws Exception{
		return new ResponseEntity<>(spPservice.swsbgspxx(sjid),HttpStatus.OK);
	}
}
