package com.gdky.restfull.framework.service;

import java.util.List;

import com.gdky.restfull.framework.entity.AsideMenu;


public interface ICommonService {

	
	/**
	 * 获取导航菜单集合
	 * @param
	 * @return AsideMenu
	 */
	public List<AsideMenu> getAsideMenu();
}
