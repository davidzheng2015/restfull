package com.gdky.restfull.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.AsideMenu;
import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.service.ICommonService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX + Constants.URI_API_FRAMEWORK)
public class MenuController {
	private static final Logger log = LoggerFactory
            .getLogger(MenuController.class);

	@Resource
	private ICommonService commonService;

	/**
	 * 获取模块菜单列表
	 * @return
	 */
	@RequestMapping(value = "/asidemenu", method = RequestMethod.GET)
	public  ResponseEntity<List<AsideMenu>> getAsideMenu() {
		List<AsideMenu> ls = commonService.getAsideMenu();
		return new ResponseEntity<>(ls,HttpStatus.OK);
	}
	/**
	 * 修改模块菜单内容
	 * @param id
	 * @param asideMenu
	 * @return
	 */
	@RequestMapping(value = "/asidemenu/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateMenu(@PathVariable("id") String id,
			@RequestBody AsideMenu asideMenu) {
		
		commonService.updateMenu(asideMenu);
		return new ResponseEntity<>(ResponseMessage.success("更新成功"),HttpStatus.OK);

	}
	
	@RequestMapping(value = "/asidemenu/{id}",method = RequestMethod.GET)
	public ResponseEntity<AsideMenu> getMenuDetail(@PathVariable("id") String id){
		AsideMenu rs = this.commonService.getMenuDetail(id);
		log.debug("get result @" + rs);
		return  new ResponseEntity<>(rs, HttpStatus.OK);
	}

	@RequestMapping(value = "/asidemenu/{id}", method = RequestMethod.POST)
	public void addMenu(@PathVariable("id") String id,
			@RequestBody Map<String, Object> var) {
		System.out.println(var);
	}

}
