package com.gdky.restfull.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.entity.Role;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Resource
	private UserService userService;

	private static final Logger log = LoggerFactory
			.getLogger(CustomUserDetailsService.class);

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List<User> ls = userService.getUser(username);

		if (ls == null || ls.size() != 1) {
			log.warn("用户不正确");
			throw new UsernameNotFoundException("User not found");
		}
	
		User user = ls.get(0);
		log.warn("获取用户");
		CustomUserDetails u = new CustomUserDetails(user);
		u.setAuthorities(this.getAuthorities(username));
		return u;
	}
	
	public List<GrantedAuthority> getAuthorities(String username){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<Role> roles = userService.getRolesByUser(username);
		for (Role role : roles) {

			// 注意：这里要ROLE_加上前缀，否则在创建角色而的时候统一加上
			authorities
					.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		}
	    return authorities;
	}
	
	
}
