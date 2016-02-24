package gov.gdgs.zs.dao;

import gov.gdgs.zs.entity.ZsSwsxxSwsxx;
import gov.gdgs.zs.untils.Pager;

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
	
	public List<Map<String,Object>> swscx(int pn,int ps){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("    a.ID,");
		sb.append("   a.dwmc,");
		sb.append("   a.ZCZJ,");
		sb.append("   a.fddbr,");
		sb.append("   a.jgzch,");
		sb.append("   b.mc,");
		sb.append("   c.mc,");
		sb.append("    d.zrs,");
		sb.append("   d.zyrs,");
		sb.append("    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		FROM");
		sb.append("    zs_jg a,");
		sb.append("    dm_jgxz b,");
		sb.append("    dm_cs c,");
		sb.append("   v_zsjgry d");
		sb.append("		WHERE");
		sb.append("   a.JGXZ_DM = b.ID");
		sb.append("  AND a.CS_DM = c.ID");
		sb.append("	AND a.ID = d.ZSJG_ID");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		Pager<Map<String, Object>> pager = Pager.create(ls, ps);
		return pager.getPagedList(pn);
	}

}
