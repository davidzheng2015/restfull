package com.gdky.restfull.api;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.AsideMenu;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;
import com.gdky.restfull.service.AuthService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX)
public class AccountController {
	
	@Resource
	AccountService accountService;
	
	@Resource
	AuthService userService;

	@RequestMapping(value="/account")
	public ResponseEntity<?> getAccount(HttpServletRequest request){
		
		User user =  accountService.getUserFromHeaderToken(request);
		
		//获取功能菜单
		List<AsideMenu> menu = accountService.getMenuByUser(user.getId());
		//获取模块访问权限
		StringBuffer permission = new StringBuffer();
		for (int i = 0;i<menu.size();i++){
			AsideMenu item = menu.get(i);
			if (item.getHref()!=null && !item.getHref().equals("")){
				permission.append(item.getHref()).append(",");
			}			
		}
		
		HashMap<String,Object> resp = new HashMap<String,Object>();
		resp.put("names", user.getNames());
		resp.put("menu", menu);
		resp.put("newMsg", false);
		return  ResponseEntity.ok(resp);
	}
}
