package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.RyglService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class RyglController {
	@Resource
	private RyglService ryglService;
	@Resource
	AccountService accountService;
	/**
	 * 中心端人员查询
	 * @param pn
	 * @param ps
	 * @param where
	 * @return
	 */
	@RequestMapping(value = "/rys", method = RequestMethod.GET )
	public ResponseEntity<Map<String, Object>> zyrycx(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where)  {
		
		return new ResponseEntity<>(ryglService.rycx(pn, ps, where),HttpStatus.OK);
		
	}
	/**
	 * 事物所端人员查询
	 * @param pn
	 * @param ps
	 * @param where
	 * @param xqTab
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/swsrycx/{cxlx}", method = RequestMethod.GET )
	public ResponseEntity<Map<String, Object>> swsrycx(
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value="where", required=false) String where,
			@PathVariable(value = "cxlx") String cxlx,HttpServletRequest request)  {
		User user =  accountService.getUserFromHeaderToken(request);
		return new ResponseEntity<>(ryglService.swsrycx(pn, ps,user.getJgId(),cxlx, where),HttpStatus.OK);
		
	}
	/**
	 * 人员详细信息
	 * @param xqTab
	 * @param gid
	 * @return
	 */
	@RequestMapping(value="/ryxx/{zyryxqTab:^[A-Za-z]+$}/{zyryId}", method = RequestMethod.GET )
	public ResponseEntity<Map<String, Object>> swsxx(
			@PathVariable(value = "zyryxqTab") String xqTab,
			@PathVariable(value = "zyryId") String gid) {
		return new ResponseEntity<>(ryglService.kzxx(xqTab, gid),HttpStatus.OK);
		
	}
		
}
