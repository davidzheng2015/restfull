package com.gdky.restfull.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdky.restfull.dao.IAsideMenuDao;
import com.gdky.restfull.entity.AsideMenu;

@Service
public class CommonService implements ICommonService {

	@Resource
	private IAsideMenuDao asideMenuDao;

	@Override
	public List<AsideMenu> getAsideMenu() {
		return asideMenuDao.getAsideMenu();
	}

	@Override
	public void updateMenu(AsideMenu asideMenu) {
		asideMenuDao.updateMenu(asideMenu);

	}

	@Override
	public AsideMenu getMenuDetail(String id) {
		return asideMenuDao.getMenuDetail(id);
	}

	@Override
	public String addMenu(Map<String, Object> node) {
		String path = asideMenuDao.getPathById((Integer) node.get("pid"));

		if (path == null) {
			path = "";
		}
		
		AsideMenu item = new AsideMenu();
		item.setPath(path + "-" + ((Integer) node.get("pid")).toString());
		item.setName((String) node.get("name"));
		item.setVisble((Integer) node.get("visble"));
		return asideMenuDao.addMenu(item);

	}
}
