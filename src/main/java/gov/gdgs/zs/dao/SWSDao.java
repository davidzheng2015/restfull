package gov.gdgs.zs.dao;

import gov.gdgs.zs.untils.Pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SWSDao {
	@Resource(name ="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> testJDBC (){
		String sql = "select * from zs_jg";
		return this.jdbcTemplate.queryForList(sql);
		
	}
	/**
	 * 
	 * @param pn
	 * @param ps
	 * @return 事务所分页查询
	 */
	public Map<String,Object> swscx(int pn,int ps){
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT ");
		sb.append("		    a.ID,");
		sb.append("	    a.dwmc,");
		sb.append("		    a.ZCZJ,");
		sb.append("		    a.fddbr,");
		sb.append("		    a.jgzch as zsbh,");
		sb.append("		    b.mc as swsxz,");
		sb.append("		    c.mc as cs,");
		sb.append("		    d.zrs,");
		sb.append("		    d.zyrs,");
		sb.append("		    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		    	FROM");
		sb.append("		    zs_jg a,");
		sb.append("		    dm_jgxz b,");
		sb.append("	    dm_cs c,");
		sb.append("	    zs_jgnj d,");
		sb.append("	    (select max(nd) as nd,ZSJG_ID from zs_jgnj group by ZSJG_ID) as v");
		sb.append("		WHERE");
		sb.append("		    a.JGXZ_DM = b.ID");
		sb.append("		    AND a.CS_DM = c.ID");
		sb.append("			AND a.ID = d.ZSJG_ID");
		sb.append("			AND v.ZSJG_ID = d.ZSJG_ID");
		sb.append("		    and d.nd = v.nd ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		Pager<Map<String, Object>> pager = Pager.create(ls, ps);
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : pager.getPagedList(pn)){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_sws", "http://localhost:8080/restfull/api/swsxx/"+rec.get("ID").toString());
			link.put("herf_zyry", "http://localhost:8080/restfull/api/zyryxx/"+rec.get("ID").toString());
			link.put("herf_cyry", "http://localhost:8080/restfull/api/cyryxx/"+rec.get("ID").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", fl);
		ob.put("totalsize", ls.size());
		ob.put("pagesize", (ls.size() + ps - 1) / ps);
		return ob;
	}
	/**
	 * 
	 * @return 事务所查询
	 */
	public Map<String,Object> swscx(){
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT ");
		sb.append("		    a.ID,");
		sb.append("	    a.dwmc,");
		sb.append("		    a.ZCZJ,");
		sb.append("		    a.fddbr,");
		sb.append("		    a.jgzch as zsbh,");
		sb.append("		    b.mc as swsxz,");
		sb.append("		    c.mc as cs,");
		sb.append("		    d.zrs,");
		sb.append("		    d.zyrs,");
		sb.append("		    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		    	FROM");
		sb.append("		    zs_jg a,");
		sb.append("		    dm_jgxz b,");
		sb.append("	    dm_cs c,");
		sb.append("	    zs_jgnj d,");
		sb.append("	    (select max(nd) as nd,ZSJG_ID from zs_jgnj group by ZSJG_ID) as v");
		sb.append("		WHERE");
		sb.append("		    a.JGXZ_DM = b.ID");
		sb.append("		    AND a.CS_DM = c.ID");
		sb.append("			AND a.ID = d.ZSJG_ID");
		sb.append("			AND v.ZSJG_ID = d.ZSJG_ID");
		sb.append("		    and d.nd = v.nd ");
		List<Map<String,Object>> ls = this.jdbcTemplate.queryForList(sb.toString());
		List<Map<String,Object>> fl = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> rec : ls){
			Map<String,Object> link = new HashMap<>();
			link.put("herf_sws", "http://localhost:8080/restfull/api/swsxx/"+rec.get("ID").toString());
			link.put("herf_zyry", "http://localhost:8080/restfull/api/zyryxx/"+rec.get("ID").toString());
			link.put("herf_cyry", "http://localhost:8080/restfull/api/cyryxx/"+rec.get("ID").toString());
			rec.put("_links", link);
			fl.add(rec);
		}
		Map<String,Object> ob = new HashMap<>();
		ob.put("data", fl);
		return ob;
	}
	
	public Map<String,Object> swsxx(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT ");
		sb.append("		    a.ID,");
		sb.append("	    a.dwmc,");
		sb.append("		    a.ZCZJ,");
		sb.append("		    a.fddbr,");
		sb.append("		    a.jgzch as zsbh,");
		sb.append("		    b.mc as swsxz,");
		sb.append("		    c.mc as cs,");
		sb.append("		    d.zrs,");
		sb.append("		    d.zyrs,");
		sb.append("		    date_format(a.swszsclsj,'%Y-%m-%d') as clsj");
		sb.append("		    	FROM");
		sb.append("		    zs_jg a,");
		sb.append("		    dm_jgxz b,");
		sb.append("	    dm_cs c,");
		sb.append("	    zs_jgnj d,");
		sb.append("	    (select max(nd) as nd,ZSJG_ID from zs_jgnj group by ZSJG_ID) as v");
		sb.append("		WHERE");
		sb.append("		    a.JGXZ_DM = b.ID");
		sb.append("		    AND a.CS_DM = c.ID");
		sb.append("			AND a.ID = d.ZSJG_ID");
		sb.append("			AND a.ID = ?");
		sb.append("			AND v.ZSJG_ID = d.ZSJG_ID");
		sb.append("		    and d.nd = v.nd ");
		String sql = "SELECT b.* FROM zs_jg a,zs_nbjgsz b where a.ID = b.jg_id and a.id = ?";
		Map<String,Object> tl = this.jdbcTemplate.queryForMap(sb.toString(),new Object[]{id});
		tl.put("NBJGSZ", this.jdbcTemplate.queryForList(sql,new Object[]{id}));
		return tl;
	}

}
