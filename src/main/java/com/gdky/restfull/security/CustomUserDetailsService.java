package com.gdky.restfull.security;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.api.RestExceptionHandler;
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

       
        return null;  
    }  
	
}
