package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.SPservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Config.URI_API_ZS)
public class SPController {
	@Resource
	AccountService accountService;
	
	@Resource
	private SPservice spPservice;
	
	/**
	 * 未审批查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spapi/wspcx", method = RequestMethod.GET)
	public ResponseEntity<?> wspcx(HttpServletRequest request ) throws Exception{
		User user =  accountService.getUserFromHeaderToken(request);
		return new ResponseEntity<>(spPservice.wspcx(user.getId()),HttpStatus.OK);
	}
	
	/**
	 * 未审批类型明细查询
	 * @param lcid
	 * @param pn
	 * @param ps
	 * @param qury
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spapi/wspcx/{cxlx}/{lcid}", method = RequestMethod.GET)
	public ResponseEntity<?> wspxq(@PathVariable(value = "lcid") int lcid,
			@PathVariable(value = "cxlx") String cxlx,
			@RequestParam(value = "pagenum", required = true) int pn,
			@RequestParam(value = "pagesize", required = true) int ps,
			@RequestParam(value = "where", required = false) String qury,
			HttpServletRequest request ) throws Exception{
		User user =  accountService.getUserFromHeaderToken(request);
		return new ResponseEntity<>(spPservice.wspmxcx(pn,ps,user.getId(),lcid,cxlx,qury),HttpStatus.OK);
	}
	
	/**
	 * 查看流程
	 * @param lid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spapi/cklc", method = RequestMethod.GET)
	public ResponseEntity<?> cklc(@RequestParam(value = "lid", required = true) int lid ) throws Exception{
		return new ResponseEntity<>(spPservice.cklc(lid),HttpStatus.OK);
	}
	
	/**
	 * 审批明细信息查询
	 * @param sjid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spapi/spmxxx/{splx}", method = RequestMethod.GET)
	public ResponseEntity<?> swsbgspxx(@PathVariable(value = "splx") String splx,
			@RequestParam(value = "sjid", required = true) String sjid) throws Exception{
		return new ResponseEntity<>(spPservice.spmxxx(splx,sjid),HttpStatus.OK);
	}
	
	/**
	 * 审批申请
	 * @param splx
	 * @param ptxm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spapi/spsq/{splx}", method = RequestMethod.POST)
	public ResponseEntity<?> spsq(@PathVariable(value = "splx") String splx,
			@RequestBody Map<String, Object> ptxm,HttpServletRequest request ) throws Exception{
		try {
			User user =  accountService.getUserFromHeaderToken(request);
			ptxm.put("uid", user.getId());
			ptxm.put("jgid", user.getJgId());
		} catch (Exception e) {
		}
		spPservice.spsq(ptxm,splx);
		return new ResponseEntity<>(ResponseMessage.success("提交成功"),HttpStatus.CREATED);
	}
	
	/**
	 * 非审批申请
	 * @param ptxm
	 * @return
	 */
	@RequestMapping(value = "/spapi/fspsq/{splx}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updatePTXM(@PathVariable(value = "splx") String splx,
			@RequestBody Map<String,Object> ptxm,HttpServletRequest request)throws Exception {
		spPservice.fspsq(ptxm,splx);
		return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);
	}
	
	/**
	 * 审批提交
	 * @param sptj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spapi/sptj/{spid}", method = RequestMethod.PUT)
	public ResponseEntity<?> sptj(@PathVariable(value = "spid") String spid,
			@RequestBody Map<String, Object> sptj,HttpServletRequest request) throws Exception{
		User user =  accountService.getUserFromHeaderToken(request);
		return new ResponseEntity<>(spPservice.sptj(sptj,spid, user.getId(), user.getNames()),HttpStatus.OK);
	}
	/**
	 * 上级驳回意见
	 * @param spid
	 * @param lcbz
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/spapi/sjbhyj/{spid}/{lcbz}", method = RequestMethod.GET)
	public ResponseEntity<?> sjbhyj(@PathVariable(value = "spid") String spid,
			@PathVariable(value = "lcbz") int lcbz) throws Exception{
		return new ResponseEntity<>(spPservice.sjbhyj(spid,lcbz),HttpStatus.OK);
	}
}
