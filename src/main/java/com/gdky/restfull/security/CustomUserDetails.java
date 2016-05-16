package com.gdky.restfull.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gdky.restfull.entity.Role;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.UserService;

public class CustomUserDetails extends User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1587641375417912954L;
	@Resource
	UserService userService;
	
	public CustomUserDetails(User u) {
        super(u);
    }
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<Role> roles = userService.getRolesByUser(this.getUsername());
		for (Role role : roles) {

			// 注意：这里要ROLE_加上前缀，否则在创建角色而的时候统一加上
			authorities
					.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		}
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
    	if(this.getAccountExpired() == 1){
    		return false;
    	}
    	return true;
        
    }

    @Override
    public boolean isAccountNonLocked() {
    	if(this.getAccountLocked() == 1){
    		return false;
    	}
    	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
    	if(this.getCredentialsExpired() == 1){
    		return false;
    	}
    	return true;
    }

    @Override
    public boolean isEnabled() {
    	if(this.getAccountEnabled() == 0){
    		return false;
    	}
    	return true;
    }


}
