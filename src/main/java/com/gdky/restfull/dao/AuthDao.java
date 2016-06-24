package com.gdky.restfull.dao;

import gov.gdgs.zs.untils.Condition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
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
		String sql = "select * from fw_role t order by t.id desc";
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
		this.jdbcTemplate.update(sql, new Object[] { roleId });

	}

	public Number insertPrivileges(Integer roleId, List<String> privileges) {
		int rs = 0;
		if (privileges.size() > 0) {
			List<Object[]> batchValue = new ArrayList<Object[]>();
			for (String str : privileges) {
				Object[] obj = new Object[] { roleId, str };
				batchValue.add(obj);
			}
			String sql = "insert into fw_role_menu (role_id,menu_id) values (?,?)";
			rs = this.jdbcTemplate.batchUpdate(sql, batchValue).length;
		}

		return rs;
	}

	public List<Map<String, Object>> getPath(List<String> menuIds) {
		Condition condition = new Condition();
		for (int i = 0; i < menuIds.size(); i++) {
			if (i == 0) {
				condition.add("id", Condition.EQUAL, menuIds.get(i));
			}
			condition.or("id", Condition.EQUAL, menuIds.get(i));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" select path from fw_menu ");
		sb.append(condition.getSql());
		sb.append(" group by path ");

		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(
				sb.toString(), condition.getParams().toArray());
		return ls;
	}

	public Number addRole(Map<String, Object> obj) {
		String name = (String) obj.get("name");
		String desc = (String) obj.get("description");
		String sql = "insert into fw_role (name,description) values(?,?)";
		return this.insertAndGetKeyByJdbc(sql, new Object[] { name, desc },
				new String[] { "id" });

	}

	public Integer delRole(Integer roleId) {
		String sql = "delete from fw_role where id = ?";
		Integer rs = this.jdbcTemplate.update(sql, new Object[] { roleId });
		return rs;

	}

	public void updateRole(Role role) {
		String sql = "update fw_role set name=?,description=? where id=?";
		this.jdbcTemplate.update(
				sql,
				new Object[] { role.getName(), role.getDescription(),
						role.getId() });

	}

	public Map<String, Object> getUsers(int page, int pageSize,
			HashMap<String, Object> where) {
		Condition condition = new Condition();
		condition.add("u.name", Condition.FUZZY, where.get("name"));
		condition.add("u.username", Condition.FUZZY, where.get("username"));
		condition.add("u.uname", Condition.EQUAL, where.get("uname"));

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  ");
		sb.append("    @rownum:=@rownum + 1 AS 'key', t . * ");
		sb.append("FROM ");
		sb.append("    fw_users t, ");
		sb.append("    (SELECT @rownum:=?) n ");
		sb.append(condition.getSql());
		sb.append("    ORDER BY t.CREATE_TIME DESC ");
		sb.append("    LIMIT ? , ? ");

		// 装嵌传值数组
		int startIndex = pageSize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(pageSize * (page - 1));
		params.add(startIndex);
		params.add(pageSize);
		

		// 获取符合条件的记录
		List<User> ls = jdbcTemplate.query(sb.toString(),
				params.toArray(),new UserRowMapper());
		String countSql = condition.getCountSql("t.id",
				"fw_users t");
		int total = jdbcTemplate.queryForObject(countSql, condition.getParams()
				.toArray(), Integer.class);
		
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pageSize", pageSize);
		obj.put("current", page);

		return obj;
	}
	
	public class UserRowMapper implements RowMapper<User> {  
		  
        @Override  
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {  
            User user = new User();  
            user.setId(rs.getInt("id"));  
            user.setUsername(rs.getString("username"));
            user.setJgId(rs.getInt("jg_id"));
            user.setNames(rs.getString("names"));
            user.setUname(rs.getString("uname"));
            user.setAccountEnabled(rs.getInt("account_enabled"));
            user.setAccountExpired(rs.getInt("account_expired"));
            user.setAccountLocked(rs.getInt("account_locked"));
            user.setCredentialsExpired(rs.getInt("credentials_expired"));
            
            return user;  
        }  
          
    }

}
