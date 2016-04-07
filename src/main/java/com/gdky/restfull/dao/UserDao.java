package com.gdky.restfull.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Resource(name ="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;


	

}
