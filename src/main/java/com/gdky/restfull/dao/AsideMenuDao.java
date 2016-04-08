package com.gdky.restfull.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;



import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.AsideMenu;

@Repository
public class AsideMenuDao<AsideMenuDao> extends BaseJdbcDao implements
		IAsideMenuDao {

	@Override
	public List<AsideMenu> getAsideMenu() {
		String sql = "select * from fw_menu  where pid is not null order by path,order_no";
		List<AsideMenu> ls = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<AsideMenu>(AsideMenu.class));
		return ls;
	}

	@Override
	public void updateMenu(AsideMenu asideMenu) {
		String sql = "update fw_menu set pid=?,name=?,href=?,order_no=?,path=?,icon=?,visble=? where id=?";
		this.jdbcTemplate.update(
				sql,
				new Object[] { asideMenu.getPid(), asideMenu.getName(),
						asideMenu.getHref(), asideMenu.getOrderNo(),
						asideMenu.getPath(), asideMenu.getIcon(),
						asideMenu.getVisble(), asideMenu.getId() });
	}

	@Override
	public AsideMenu getMenuDetail(String id) {
		String sql = "select * from fw_menu where id = ?";
		AsideMenu rs = this.jdbcTemplate.queryForObject(sql,
				new Object[] { id }, new BeanPropertyRowMapper<AsideMenu>(AsideMenu.class));
		return rs;
	}
	
	@Override
	public String getPathById(Integer id) {
		String sql = "select path from "+ Constants.PROJECT_SCHEMA+"fw_menu where id = ?";
		String rs = jdbcTemplate.queryForObject(sql, new Object[]{id},String.class);
		return rs;
	}

	@Override
	public String addMenu(final AsideMenu item) {
		final StringBuffer sb  =  new StringBuffer("insert into "+ Constants.PROJECT_SCHEMA +"fw_menu ");
		sb.append("(pid,name,path,visble) ");
		sb.append("values (?,?,?,?)");
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps =
			                connection.prepareStatement(sb.toString(), new String[] {"id"});
			            ps.setInt(1, item.getPid());
			            ps.setString(2,item.getName());
			            ps.setString(5,item.getPath());
			            ps.setInt(7, item.getVisble());
			            return ps;
			        }
			    },
			    keyHolder);
		return keyHolder.getKey().toString();
	}



}
