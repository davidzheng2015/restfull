package com.gdky.restfull.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.dao.IAsideMenuDao;
import com.gdky.restfull.dao.UserDao;
import com.gdky.restfull.entity.AsideMenu;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.security.TokenUtils;

@Service
public class AccountService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private IAsideMenuDao menuDao;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	public List<AsideMenu> getMenuByUser(int userId) {
		List<AsideMenu> ls = menuDao.getAccoutMenu(userId);
		return ls;
	}
	
	public User getUserFromHeaderToken (HttpServletRequest request) {
		String tokenHeader = Constants.AUTH_HEADER_NAME;
		String authToken = request.getHeader(tokenHeader);
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		User user = userService.getUser(username);		
		return user;
	}
	
	public String getPermissionByUser (User user){
		List<AsideMenu> menu = this.getMenuByUser(user.getId());
		Md5PasswordEncoder  encoder = new Md5PasswordEncoder();
		StringBuffer permission = new StringBuffer();
		for (int i = 0;i<menu.size();i++){
			AsideMenu item = menu.get(i);
			if (item.getHref()!=null && !item.getHref().equals("")){
				permission.append(encoder.encodePassword(item.getHref(),null)).append(",");
			}			
		}
		return permission.toString();
	}
}
