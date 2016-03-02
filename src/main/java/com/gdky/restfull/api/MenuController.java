package com.gdky.restfull.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.Constants;
import com.gdky.restfull.framework.entity.AsideMenu;
import com.gdky.restfull.framework.service.ICommonService;


@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_API_FRAMEWORK)
public class MenuController {
	
	@Resource
	private ICommonService commonService;
	
	@RequestMapping(value = "/asidemenu", method = RequestMethod.GET)
	public  List<AsideMenu> getAsideMenu() {
		return commonService.getAsideMenu();
	}

}
