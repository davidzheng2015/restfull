package com.gdky.restfull.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
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

	public void delPrivileges(Integer roleId) {
		authDao.delPrivileges(roleId);		
	}

	public void insertPrivileges(Integer roleId, List<String> privileges) {
		List<Map<String,Object>> rows = authDao.getPath(privileges);
		String[] nodes = new String[0] ;
		for(Map<String,Object> row : rows){
			String path = (String)row.get("path");
			nodes = ArrayUtils.addAll(nodes, path.split("-"));			
		}
		List<String> list = Arrays.asList(nodes);
		list = removeZero(list);
		privileges.addAll(list);
		privileges = unique(privileges);

		Number rs = authDao.insertPrivileges(roleId, privileges);
		
	}
	public static List<String> unique(List<String> list) {  
	    // List_unique  
		HashSet<String> set = new HashSet<String>(list);
		list.clear();
		list.addAll(set);
		return list;
	}
	
	public static List<String> removeZero(List<String> items){
		List<String> list = new ArrayList<String>();
		for(String str : items){
			if(!str.equals("000")){
				str = str.replaceFirst("^0*", "");
				list.add(str);
			}
		}
		return list;
	}

	public Number addRole(Map<String, Object> obj) {
		return authDao.addRole(obj);		
	}

	public void delRole(Integer roleId) {
		authDao.delPrivileges(roleId);	
		authDao.delRole(roleId);		
	}

	public void updateRole(Role role) {
		authDao.updateRole(role);		
	}
	
	


}
