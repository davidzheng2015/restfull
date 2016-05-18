package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.LSJLCXServcie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Config.URI_API_ZS)
public class LSJLCXController {
	@Resource
	private LSJLCXServcie lsjlcxServcie;
	
	@RequestMapping(value = "/swslsjl/bglsjl1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swsbglsjlcx(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.swsbglsjlcx(pn, ps, where),HttpStatus.OK);

	}
	
	@RequestMapping(value = "/swslsjl/yhbsws1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> yhbswscx(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.yhbswscx(pn, ps, where),HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/swslsjl/yzxsws1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> yzxswscx(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.yzxswscx(pn, ps, where),HttpStatus.OK);
		
	}
	@RequestMapping(value = "/swslsjl/snbgjl1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swssnbgjl(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.swssnbgjl(pn, ps, where),HttpStatus.OK);
		
	}
	@RequestMapping(value = "/swslsjl/snzsjl1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swssnzsjl(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.swssnzsjl(pn, ps, where),HttpStatus.OK);
		
	}
	@RequestMapping(value = "/swslsjl/snzzfjl1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swssnzzfjl(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.swssnzzfjl(pn, ps, where),HttpStatus.OK);
		
	}
	@RequestMapping(value = "/swslsjl/snzjjl1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swssnzjjl(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.swssnzjjl(pn, ps, where),HttpStatus.OK);
		
	}
	@RequestMapping(value = "/swslsjl/snzxjl1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swssnzxjl(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.swssnzxjl(pn, ps, where),HttpStatus.OK);
		
	}
	@RequestMapping(value = "/swslsjl/snbdjl1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swssnbdjl(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(lsjlcxServcie.swssnbdjl(pn, ps, where),HttpStatus.OK);
		
	}
}
