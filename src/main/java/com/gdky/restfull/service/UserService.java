package com.gdky.restfull.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdky.restfull.dao.UserDao;
import com.gdky.restfull.entity.User;

@Service
public class UserService {
	
	@Resource
	private UserDao userDao;

	public User getUser(String userName){
		return userDao.getUser(userName);
	}
}
