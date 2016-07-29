package gov.gdgs.zs.api;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.HyryqktjService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 系统统计报表-行业人员统计情况
 * @author dell1
 *
 */
@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class HyryqktjController {
	@Autowired
	private HyryqktjService hyryqktjService;
	
	@RequestMapping(value = "/xttjbb/hyryqktj", method = RequestMethod.GET)
	public ResponseEntity<?> getXttjbb(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value="where", required=false) String where){
		Map<String, Object> obj = hyryqktjService.getHyryqktj(page,pageSize,where);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
}
