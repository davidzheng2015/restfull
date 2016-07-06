package gov.gdgs.zs.api;

import java.util.Map;

import gov.gdgs.zs.configuration.Config;
import gov.gdgs.zs.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public  ResponseEntity<Map<String,Object>> AddCustomers(){ 

		Map<String,Object> obj = customService.AddCustomer();
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}
	
	/**
	 * 修改客户信息
	 * @para
	 *
	 */
	@RequestMapping(value = "/customers/{hashId}", method = RequestMethod.PUT)
	public  ResponseEntity<Map<String,Object>> updateCustomer(@PathVariable String hashId){ 

		Map<String,Object> obj = customService.updateCustomer();
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	/**
	 * 修改客户信息
	 * @para
	 *
	 */
	@RequestMapping(value = "/customers/{hashId}", method = RequestMethod.DELETE)
	public  ResponseEntity<?> delCustomer(@PathVariable String hashId){ 

		Map<String,Object> obj = customService.delCustomer();
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
}
