package com.gdky.restfull.dao;

import gov.gdgs.zs.untils.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.entity.Privileges;
import com.gdky.restfull.entity.Role;
import com.gdky.restfull.entity.User;

@Repository
@Transactional
public class AuthDao extends BaseJdbcDao {

	public List<User> getUser(String userName) {
		String sql = "select * from fw_users where username = ?";
		List<User> ls = this.jdbcTemplate.query(sql, new Object[] { userName },
				new BeanPropertyRowMapper<User>(User.class));
		return ls;
	}

	public List<Role> getRolesByUser(String userName) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select r.* from fw_users u, fw_user_role ur ,fw_role  r ");
		sb.append(" where u.ID = ur.USER_ID ");
		sb.append(" and ur.ROLE_ID = r.ID ");
		sb.append(" and u.USERNAME =? ");
		List<Role> ls = this.jdbcTemplate.query(sb.toString(),
				new Object[] { userName }, new BeanPropertyRowMapper<Role>(
						Role.class));
		return ls;
	}

	public List<Role> getRoles() {
		String sql = "select * from fw_role t order by t.id";
		List<Role> ls = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Role>(Role.class));
		return ls;
	}

	public List<Privileges> getPrivileges(Integer roleId) {
		String sql = "select * from fw_role_menu where role_id = ?";
		List<Privileges> ls = this.jdbcTemplate.query(sql,
				new Object[] { roleId }, new BeanPropertyRowMapper<Privileges>(
						Privileges.class));
		return ls;

	}

	public void delPrivileges(Integer roleId) {
		String sql = "delete from fw_role_menu where role_id = ?";
		this.jdbcTemplate.update(sql,new Object[]{roleId});
		
	}

	public Number insertPrivileges(Integer roleId, List<String> privileges) {
		int rs = 0;
		if(privileges.size() >0){
			List<Object[]> batchValue = new ArrayList<Object[]> ();
			for(String str : privileges){
				Object[] obj = new Object[]{roleId,str};
				batchValue.add(obj);
			}
			String sql = "insert into fw_role_menu (role_id,menu_id) values (?,?)";
			rs = this.jdbcTemplate.batchUpdate(sql, batchValue).length;
		}
		
		return rs;				
	}

	public List<Map<String,Object>> getPath(List<String> menuIds) {
		Condition condition = new Condition();
		for (int i = 0; i< menuIds.size();i++){
			if(i==0){
				condition.add("id", Condition.EQUAL, menuIds.get(i));
			}
			condition.or("id", Condition.EQUAL, menuIds.get(i));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" select path from fw_menu ");
		sb.append(condition.getSql());
		sb.append(" group by path ");
		
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString(), condition.getParams()
				.toArray());

		return ls;
	}

}
