package com.gdky.restfull.api;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.Constants;
import com.gdky.restfull.framework.service.CommonService;


@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_API_FRAMEWORK)
public class MenuController {
	
	@Resource
	private CommonService commonService;
	
	@RequestMapping(value = "/asidemenu/", method = RequestMethod.GET)
	public  AsideMenu getAsideMenu() {
		return commonService.getAsideMenu();
	}

}
