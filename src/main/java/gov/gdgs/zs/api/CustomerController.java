package gov.gdgs.zs.api;

import java.util.Map;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.entity.ResponseMessage;

/**
 * 客户信息管理
 * @author ming
 *
 */
@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class CustomerController {
	
	@Autowired
	private CustomerService customService;

	/**
	 * 获取客户信息
	 * @para
	 *
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public  ResponseEntity<?> getCustomers(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value="jid", required=true) String jgid,
			@RequestParam(value="where", required=false) String where){ 

		Map<String,Object> obj = customService.getCustomers(page,pageSize,jgid,where);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}

	
	/**
	 * 新增客户信息
	 * @para
	 *
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public  ResponseEntity<?> addCustomers(
			@RequestBody Map<String,Object> obj){
		
		customService.addCustomer(obj);
		return new ResponseEntity<>(ResponseMessage.success("添加成功"),HttpStatus.CREATED);
	}
	
	/**
	 * 修改客户信息
	 * @para
	 *
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
	public  ResponseEntity<?> updateCustomer(@PathVariable String id,@RequestBody Map<String,Object> obj){ 

		customService.updateCustomer(id,obj);
		return new ResponseEntity<>(ResponseMessage.success("修改成功"),HttpStatus.OK);
	}
	
	/**
	 * 修改客户信息
	 * @para
	 *
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
	public  ResponseEntity<?> delCustomer(@PathVariable String id){ 

		customService.delCustomer(id);
		return new ResponseEntity<>(ResponseMessage.success("修改成功"),HttpStatus.OK);
	}
}
