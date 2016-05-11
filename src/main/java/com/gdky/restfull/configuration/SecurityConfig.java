package com.gdky.restfull.configuration;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.gdky.restfull.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource
	private CustomUserDetailsService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception
    {
        web
            .ignoring()
                // All of Spring Security will ignore the requests
                .antMatchers("/resources/**")
                .antMatchers(HttpMethod.POST, "/login");
    }
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
            http   
                .authorizeRequests()   
    			.antMatchers("/api/**")
    			.permitAll()
    		.and()
                .authorizeRequests()   
    			.antMatchers("/api/auth/**")
    			.authenticated()
    		.and()
    			.authorizeRequests()   
    			.anyRequest()
    			.permitAll()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .httpBasic()
            .and()
              	.formLogin()
            .and()
                .csrf().disable();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder())
                    .withUser("admin").password("test").authorities("ROLE_ADMIN")
                    .and()
                        .withUser("user").password("test").authorities("ROLE_USER");
	}
	
	 @Autowired  
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {  

	        auth.userDetailsService(userDetailsService);  
	        //加载授权信息  
	        auth.authenticationProvider(authenticationProvider);  
	    }  


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	public PlaintextPasswordEncoder passwordEncoder() {
		return new PlaintextPasswordEncoder();
	}
}
