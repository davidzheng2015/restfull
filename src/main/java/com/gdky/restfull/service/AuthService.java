package com.gdky.restfull.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.dao.AuthDao;
import com.gdky.restfull.entity.Privileges;
import com.gdky.restfull.entity.Role;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.security.TokenUtils;
import com.gdky.restfull.utils.HashIdUtil;

@Service
public class AuthService {
	
	@Resource
	private AuthDao authDao;
	
	public User getUser(String userName){
		List<User> ls =  authDao.getUser(userName);
		if (ls.size()!=1){
			return null;
		}
		return ls.get(0);
	}

	public List<Role> getRolesByUser(String userName) {
		return authDao.getRolesByUser(userName);
	}
	
	public List<Role> getRoles(){
		return authDao.getRoles();
	}

	public List<Privileges> getPrivileges(Integer roleId) {
		return authDao.getPrivileges(roleId);
	}

	public void delPrivileges(String roleId) {
		authDao.delPrivileges(roleId);		
	}

	public void insertPrivileges(String roleId, List<String> privileges) {
		List<Map<String,Object>> paths = authDao.getPath(privileges);
		for(Map<String,Object> path : paths){
			
		}
		Number rs = authDao.insertPrivileges(roleId, privileges);
		
	}
	


}
