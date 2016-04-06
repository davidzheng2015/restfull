package com.gdky.restfull.service;

import java.util.List;
import java.util.Map;

import com.gdky.restfull.entity.AsideMenu;


public interface ICommonService {

	
	/**
	 * 获取导航菜单集合
	 * @param
	 * @return AsideMenu
	 * @throws Exception 
	 */
	public List<AsideMenu> getAsideMenu() ;

	/**
	 * 更新菜单节点
	 * @throws Exception 
	 */
	public void updateMenu(AsideMenu asideMenu);

	public AsideMenu getMenuDetail(String id);
}
