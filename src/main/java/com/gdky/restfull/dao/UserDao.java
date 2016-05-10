package com.gdky.restfull.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.entity.User;

@Repository
@Transactional
public class UserDao extends BaseJdbcDao{

	public User getUser(String userName) {
		String sql = "select * from fw_users where username = ?";
		User user = this.jdbcTemplate.queryForObject(sql, User.class, new Object[]{userName});
		return user;
	}

}
