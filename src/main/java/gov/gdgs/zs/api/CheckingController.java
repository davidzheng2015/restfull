package gov.gdgs.zs.api;

import javax.annotation.Resource;
import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.CheckingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Config.URI_API_ZS)
public class CheckingController {

	@Resource
	private CheckingService chService;
	/**
	 * 判断审批中
	 * @param jgid
	 * @param splx
	 * @return
	 */
	@RequestMapping(value = "/commont/checksping/{splx}/{id}", method = { RequestMethod.GET })
	public ResponseEntity<?> checkSPing(@PathVariable(value = "id") String jgid,
			@PathVariable(value = "splx") String splx) {
		return new ResponseEntity<>(chService.checkSPing(splx,jgid),HttpStatus.OK);
	}
	/**
	 * 判断身份证号码是否重复
	 * @param sfzh
	 * @return
	 */
	@RequestMapping(value = "/commont/checksfzh/{sfzh}", method = { RequestMethod.GET })
	public ResponseEntity<?> checkSFZH(@PathVariable(value = "sfzh") String sfzh) {
		return new ResponseEntity<>(chService.checkSFZH(sfzh),HttpStatus.OK);
	}
	/**
	 * 判断是否驳回
	 * @param spid
	 * @return
	 */
	@RequestMapping(value = "/commont/checkisbh/{spid}", method = { RequestMethod.GET })
	public ResponseEntity<?> checkIsBH(@PathVariable(value = "spid") String spid) {
		return new ResponseEntity<>(chService.checkIsBH(spid),HttpStatus.OK);
	}
}
