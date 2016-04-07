package com.gdky.restfull.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
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

}
