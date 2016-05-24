package com.gdky.restfull.api;

import gov.gdgs.zs.configuration.Config;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hashids.Hashids;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.AsideMenu;
import com.gdky.restfull.exception.ResourceNotFoundException;
import com.gdky.restfull.service.AccountService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX)
public class AccountController {
	
	@Resource
	AccountService accountService;

	@RequestMapping(value="/account/{hash}")
	public ResponseEntity<?> getAccount(@PathVariable String hash){
		Hashids hashids = new Hashids(Config.HASHID_SALT,Config.HASHID_LEN);
		long[] id = hashids.decode(hash);
		if(id.length<1){
			throw new ResourceNotFoundException(hash);
		}
		List<AsideMenu> menu = accountService.getMenuFromUser((int)id[0]);
		
		HashMap<String,Object> resp = new HashMap<String,Object>();
		resp.put("menu", menu);
		return  ResponseEntity.ok(resp);
	}
}
