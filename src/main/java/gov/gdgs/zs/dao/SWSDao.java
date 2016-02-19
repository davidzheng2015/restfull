package gov.gdgs.zs.dao;

import gov.gdgs.zs.entity.ZsSwsxxSwsxx;

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
public class SWSDao {
	@Resource(name ="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, ZsSwsxxSwsxx> getAllSwsxx(){
	String sql = "select * from zs_swsxx";
	  final Map<String, ZsSwsxxSwsxx> swsxxMap = new HashMap<String,ZsSwsxxSwsxx>();
	  
	this.jdbcTemplate.query(sql, new RowMapper<Menu>(){

		public Menu mapRow(ResultSet rs, int arg1) throws SQLException {
	
			ZsSwsxxSwsxx swsxx = new ZsSwsxxSwsxx();
			swsxx.setId(rs.getString("ID"));
			swsxx.setJgmc(rs.getString("JGMC"));
			swsxx.setFddbr(rs.getString("FDDBR"));
			swsxx.setSglzxsbwh(rs.getString("SGLZXSBWH"));
			swsxx.setSglzxsbsj(rs.getDate("SGLZXSBSJ"));
			swsxx.setZsbh(rs.getString("ZSBH"));
			swsxx.setJgxz(rs.getString("JGXZ"));
			
			swsxxMap.put(swsxx.getId(),swsxx);
			return null;
		}
		
	});
	return swsxxMap;
		}
	
	public List<Map<String,Object>> testJDBC (){
		String sql = "select * from zs_swsxx";
		return this.jdbcTemplate.queryForList(sql);
		
	}

}
