package com.gdky.restfull.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdky.restfull.dao.IAsideMenuDao;
import com.gdky.restfull.dao.UserDao;
import com.gdky.restfull.entity.AsideMenu;

@Service
public class AccountService {

	@Resource
	UserDao userDao;
	
	@Resource
	IAsideMenuDao menuDao;
	
	public List<AsideMenu> getMenuFromUser(int userId) {
		List<AsideMenu> ls = menuDao.getAccoutMenu(userId);
		return ls;
	}
}
