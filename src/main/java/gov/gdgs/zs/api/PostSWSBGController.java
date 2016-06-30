package gov.gdgs.zs.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import gov.gdgs.zs.configuration.Config;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Config.URI_API_ZS)
public class PostSWSBGController {


	//测试附件
	@RequestMapping(value = "/swszx/swszxfjPost1", method = RequestMethod.POST)
	public ResponseEntity<?> testUpload(HttpServletRequest request ) throws Exception{
		return new ResponseEntity<>(ResponseMessage.success("提交成功"),HttpStatus.CREATED);
	}
}
