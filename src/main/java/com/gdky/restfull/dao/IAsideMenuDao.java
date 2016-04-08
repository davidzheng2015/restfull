package com.gdky.restfull.dao;

import java.util.List;
import java.util.Map;

import com.gdky.restfull.entity.AsideMenu;

public interface IAsideMenuDao {
	
	public List<AsideMenu> getAsideMenu() ;

	public void updateMenu(AsideMenu asideMenu);

	public AsideMenu getMenuDetail(String id);

	public String addMenu(AsideMenu item);


	/**
	 * 通过id返回该记录的path
	 * @para id
	 */
	public String getPathById(Integer id);

}
