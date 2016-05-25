package gov.gdgs.zs.api;

import java.util.Map;

import gov.gdgs.zs.service.SwsService;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.exception.ResourceAlreadyExistsExcepiton;


@RestController
@RequestMapping(value = "/pub/api")
public class PubApiController {
	
	@Resource
	SwsService swsService;

	@RequestMapping(value="/jgs" ,method = { RequestMethod.GET })
	public ResponseEntity<?> getSws(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pagesize", required = true) int pagesize,
			@RequestParam(value="where", required=false) String where){
		return new ResponseEntity<>(swsService.swscx(page, pagesize, where),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fzysws", method = RequestMethod.POST)
	public ResponseEntity<?> addFzyswsBa (@RequestBody Map<String, Object> obj){
		String sfzh = (String)obj.get("sfzh");
		if(sfzh != null && sfzh.equals("0000")){
			throw new ResourceAlreadyExistsExcepiton();
		}
		ResponseMessage rm = new ResponseMessage(
				ResponseMessage.Type.success, "备案申请提交成功");
		return new ResponseEntity<>(rm,HttpStatus.CREATED);
	}
}
