package com.gdky.restfull.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDao {
	@Resource(name ="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;


	

}
