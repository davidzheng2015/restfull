package com.gdky.restfull.framework.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdky.restfull.framework.dao.IAsideMenuDao;
import com.gdky.restfull.framework.entity.AsideMenu;

@Service
public class CommonService implements ICommonService {
	
	@Resource
	private IAsideMenuDao asideMenuDao;

	@Override
	public List<AsideMenu> getAsideMenu() {
		return asideMenuDao.getAsideMenu();
	}

}
