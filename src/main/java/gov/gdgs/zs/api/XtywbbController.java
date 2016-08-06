package gov.gdgs.zs.api;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.XtywbbService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 系统业务报表
 * @author dell1
 *
 */
@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class XtywbbController {
	@Autowired
	private XtywbbService xtywbbService;
	
	@RequestMapping(value = "/xtywbb/ndjysrtj", method = RequestMethod.GET)
	public ResponseEntity<?> getNdjysrtj(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value="where", required=false) String where){
		Map<String, Object> obj = xtywbbService.getNdjysrtj(page, pageSize, where);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
}
