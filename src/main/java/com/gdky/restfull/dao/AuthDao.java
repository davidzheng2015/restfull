package com.gdky.restfull.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.entity.Role;

@Repository
public class AuthDao extends BaseJdbcDao{
	
	public List<Role> getRoles (){
		String sql = "select * from fw_role order t by t.id";
		List<Role> ls = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
		return ls;
	}

}
