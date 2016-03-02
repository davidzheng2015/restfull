package com.gdky.restfull.framework.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.framework.entity.AsideMenu;

@Repository
public class AsideMenuDao<AsideMenuDao> extends BaseJdbcDao implements IAsideMenuDao {
	
//	@Resource(name ="jdbcTemplate")
//	private JdbcTemplate jt;
	
	@Override
	public List<AsideMenu> getAsideMenu() {
		String sql  = "select * from fw_menu";
		List<AsideMenu> ls = this.jdbcTemplate.query(sql,new BeanPropertyRowMapper<AsideMenu>(AsideMenu.class));
		return ls;
	}

}
