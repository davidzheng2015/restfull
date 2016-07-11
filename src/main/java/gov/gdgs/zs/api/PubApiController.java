package gov.gdgs.zs.api;

import gov.gdgs.zs.service.CheckingService;
import gov.gdgs.zs.service.SPservice;
import gov.gdgs.zs.service.SwsService;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.exception.InvalidRequestException;
import com.gdky.restfull.exception.ResourceAlreadyExistsExcepiton;
import com.gdky.restfull.service.AuthService;


@RestController
@RequestMapping(value = "/pub/api")
public class PubApiController {
	
	@Resource
	SwsService swsService;
	
	@Autowired
	CheckingService checkingService;
	
	@Autowired
	private SPservice spService;
	
	@Autowired
	private AuthService authService;
	

	//机构查询
	@RequestMapping(value="/jgs" ,method = { RequestMethod.GET })
	public ResponseEntity<?> getSws(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pagesize", required = true) int pagesize,
			@RequestParam(value="where", required=false) String where){
		return new ResponseEntity<>(swsService.swscx(page, pagesize, where),HttpStatus.OK);
	}
	
	//非执业注师备案提交
	@RequestMapping(value = "/ba/fzysws", method = RequestMethod.POST)
	public ResponseEntity<?> addFzyswsBa (@RequestBody Map<String, Object> obj) throws Exception{
		String sfzh = (String)obj.get("SFZH");
		if(sfzh == null && sfzh.isEmpty()){
			throw new InvalidRequestException("填报资料不全：缺失身份证号");			
		}else if(!checkingService.checkSFZH(sfzh)){
			throw new ResourceAlreadyExistsExcepiton();
		}
		
		spService.spsq(obj,"fzyswsbasq");
		ResponseMessage rm = new ResponseMessage(
				ResponseMessage.Type.success, "备案申请提交成功");
		return new ResponseEntity<>(rm,HttpStatus.CREATED);
	}
	
	
	//非执业备案通过列表

	//非执业备案进度查询   
	@RequestMapping(value="/ba/fzysws/{sfzh}",method = RequestMethod.GET)
		public ResponseEntity<?> getFzyswsBa(@PathVariable String sfzh){
		Map<String,Object> rm = spService.getFzyswsBa(sfzh);
		return  ResponseEntity.ok(rm);
		
	}
	
	//执业转执业通过列表
	
	//执业转非执业进度查询
	
	//非执业转籍申请
	
	//报备号码查询
	
	

	
}
