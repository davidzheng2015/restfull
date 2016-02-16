package com.gdky.restfull.framework.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.framework.entity.Menu;

@Repository
public class MenuDao {
	@Resource(name ="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public Map<Integer,Menu> getAllMenu(){
		String sql ="select * from fw_menu ";
		final Map<Integer,Menu> menusMap = new HashMap<Integer,Menu>();

		this.jdbcTemplate.query(sql, new RowMapper<Menu>(){

			@Override
			public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
				Menu menu = new Menu();
				menu.setId(rs.getInt("ID"));
				menu.setName(rs.getString("NAME"));
				menu.setOrderNo(rs.getInt("ORDER_NO"));
				menu.setPath(rs.getString("PATH"));
				menu.setPid(rs.getInt("PID"));
				menu.setUrl(rs.getString("url"));
				menu.setVisible(rs.getString("VISIBLE"));
				menusMap.put(menu.getId(), menu);
				return null;
			}
			
		});
		return menusMap;		
	}

}
