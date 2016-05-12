package com.gdky.restfull.configuration;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.gdky.restfull.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomUserDetailsService userDetailsService;

    @Resource
    private AuthenticationProvider authProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
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
                .antMatchers("/auth/api/**")
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
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(
                plaintextPasswordEncoder());
        // 加载授权信息
        auth.authenticationProvider(authProvider);
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(plaintextPasswordEncoder())
                .withUser("admin").password("test").authorities("ROLE_ADMIN")
                .and().withUser("user").password("test").authorities("ROLE_USER");
    }*/

    @Bean
    public ShaPasswordEncoder shaPasswordEncoder() {
        return new ShaPasswordEncoder();
    }

    @Bean
    public PlaintextPasswordEncoder plaintextPasswordEncoder() {
        return new PlaintextPasswordEncoder();
    }

}
