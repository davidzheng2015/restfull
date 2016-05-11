package com.gdky.restfull.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.api.RestExceptionHandler;
import com.gdky.restfull.entity.Role;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Resource(name = "userService")  
    private UserService userService;  
	
	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
  
    @Override  @Transactional(readOnly=true)  
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
       User user = userService.getUser(username);  

       if (user == null) {
    	   log.warn("不存在该用户");
           throw new UsernameNotFoundException("user not found");
       }
       return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),  
               getGrantedAuthorities(user));
    }  
    
    protected List<GrantedAuthority> getGrantedAuthorities(User user){  
        List<GrantedAuthority> authorities = new ArrayList();
        List<Role> roles = userService.getRolesByUser(user.getUsername());
        for (Role role :roles) {  

            //注意：这里要ROLE_加上前缀，否则在创建角色而的时候统一加上  
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));  
        }  
        return authorities;  
    }  
	
}
