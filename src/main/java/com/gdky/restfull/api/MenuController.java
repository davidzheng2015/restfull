package com.gdky.restfull.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.AsideMenu;
import com.gdky.restfull.service.ICommonService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_API_FRAMEWORK)
public class MenuController {

	@Resource
	private ICommonService commonService;

	@RequestMapping(value = "/asidemenu", method = RequestMethod.GET)
	public List<AsideMenu> getAsideMenu() {
		return commonService.getAsideMenu();

	}

	@RequestMapping(value = "/asidemenu/{id}", method = RequestMethod.PUT)
	public Map<String, Object> updateMenu(@PathVariable("id") String id,
			@RequestBody AsideMenu asideMenu) {
		
		commonService.updateMenu(asideMenu);
		Map<String, Object> respond = new LinkedHashMap<String, Object>();
		respond.put("message", "操作成功");
		return respond;

	}

	@RequestMapping(value = "/asidemenu/{id}", method = RequestMethod.POST)
	public void addMenu(@PathVariable("id") String id,
			@RequestBody Map<String, Object> var) {
		System.out.println(var);
	}

}
