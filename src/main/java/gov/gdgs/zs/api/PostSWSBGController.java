package gov.gdgs.zs.api;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.PostSWSBGService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class PostSWSBGController {

	@Resource
	AccountService accountService;
	
	@Resource
	private PostSWSBGService poService;
	
	@RequestMapping(value = "/swsbg/swsjgGet1", method = { RequestMethod.GET })
	public ResponseEntity<Map<String, Object>> swscx(
			@RequestParam(value = "jgid", required = true) int jgid)  {
		return new ResponseEntity<>(poService.swsbgPost(jgid),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/swsbg/swsjgPut1", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updatePTXM(
			@RequestBody Map<String, Object> ptxm) {
		poService.updatePTXM(ptxm);
			return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/swsbg/swsjgPost1", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> updateSPXM(HttpServletRequest request,
			@RequestBody Map<String, Object> ptxm ) {
		User user =  accountService.getUserFromHeaderToken(request);
		poService.updateSPXM(ptxm,user.getId(),user.getJgId());
		return new ResponseEntity<>(ResponseMessage.success("提交成功"),HttpStatus.CREATED);
	}
}
