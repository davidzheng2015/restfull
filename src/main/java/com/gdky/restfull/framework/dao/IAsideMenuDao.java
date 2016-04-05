package com.gdky.restfull.framework.dao;

import java.util.List;
import java.util.Map;

import com.gdky.restfull.framework.entity.AsideMenu;

public interface IAsideMenuDao {
	
	public List<AsideMenu> getAsideMenu() ;

	public void updateMenu(AsideMenu asideMenu);

}
